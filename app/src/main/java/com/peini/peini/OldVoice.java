package com.peini.peini;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class OldVoice extends AppCompatActivity {

    //状态
    private boolean recordState = false;
    private boolean playState = false;

    //通知
    private static final String LOG_TAG = "RRCORD_TEST";
    //文件路径
    private String fileName = null;

    //按钮
    private Button oldRecordButton;
    private Button oldPersonCenterButton;

    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   //去掉标题栏
        setContentView(R.layout.activity_old_voice);
        initViews();

        oldRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!recordState){

                    if(playState){
                        playState = false;
                        mPlayer.release();
                        mPlayer = null;
                    }

                    recordState = true;
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setOutputFile(fileName);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                    mRecorder.start();
                }else{
                    recordState = false;
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder = null;

                    if(!playState){
                        playState = true;
                        mPlayer = new MediaPlayer();
                        try{
                            mPlayer.setDataSource(fileName);
                            mPlayer.prepare();
                            mPlayer.start();
                        }catch(IOException e){
                            Log.e(LOG_TAG,"播放失败");
                        }
                    }else{
                        playState = false;
                        mPlayer.release();
                        mPlayer = null;
                    }
                }
            }
        });
    }

    private void initViews(){
        oldRecordButton = (Button) findViewById(R.id.oldRecord);
        oldPersonCenterButton = (Button) findViewById(R.id.oldPersonCenter);

        //设置sdcard的路径
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";
    }
}
