package br.android.simplemediacontroller.player.player;

import android.content.Context;

import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import br.android.simplemediacontroller.player.utils.Cypher;


/**
 * Created by diogojayme on 12/18/15.
 */
public class FileReader {
    private Context context;
    private  ReadFileCallback callback;

    public FileReader(Context context, ReadFileCallback callback){
        this.context = context;
        this.callback = callback;
    }

    public FileReader download(String pathFile, String outputName, String extension){

        if(pathFile == null || outputName == null || extension == null)
            throw new NullPointerException("Input data are null");

        String fullPath = String.format("%s/%s/%s", android.os.Environment.getExternalStorageDirectory(), pathFile, Cypher.md5(outputName));
        File file = new File(fullPath);
        Crypto crypto = new Crypto(new SharedPrefsBackedKeyChain(context), new SystemNativeCryptoLibrary());

        try {
            byte[] data = crypto.decrypt(readFile(file), new Entity(Cypher.md5(outputName)));

            File tempMp3 = File.createTempFile("temp", extension, context.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(data);
            fos.close();

            onCallback(tempMp3, callback);
        }catch(Exception e){
            onCallback("decrypt error" + e.getMessage(), callback);
        }

        return this;
    }

    public byte[] readFile(File file) {

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            long maxLength = randomAccessFile.length();
            int length = (int) maxLength;

            if (length != maxLength)
                throw new IOException("Tamanho do arquivo >= 2 GB");

            byte[] data = new byte[length];
            randomAccessFile.readFully(data);
            randomAccessFile.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    void onCallback(Object obj, ReadFileCallback callback){
        if(obj instanceof File){
            callback.onReadSuccess((File) obj);
        }else{
            callback.onReadFailed((String) obj);
        }
    }

    interface ReadFileCallback{
        void onReadSuccess(File file);
        void onReadFailed(String message);
    }

}
