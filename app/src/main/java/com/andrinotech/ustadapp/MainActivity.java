import android.support.v7.app.AppCompatActivity;

//package com.andrinotech.ustadapp;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//
//import com.andrinotech.ustadapp.helper.FileHelper;
//import com.andrinotech.ustadapp.helper.Utilforbitmap;
//import com.andrinotech.ustadapp.utils.CommonUtils;
//
//
public class MainActivity extends AppCompatActivity {
//
////
////    ImageView capturedImageView;
////    Button btnOpen, btnSave;
////    TextView textInfo, textMaxDur, textCurDur;
////    SeekBar timeFrameBar;
////
////    long maxDur;
////
////    MediaMetadataRetriever mediaMetadataRetriever = null;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////
////        btnOpen = (Button)findViewById(R.id.open);
////        btnSave = (Button)findViewById(R.id.save);
////        textInfo = (TextView)findViewById(R.id.info);
////        textMaxDur = (TextView)findViewById(R.id.maxdur);
////        textCurDur = (TextView)findViewById(R.id.curdur);
////        timeFrameBar = (SeekBar)findViewById(R.id.timeframe);
////
////        capturedImageView = (ImageView) findViewById(R.id.capturedimage);
////
////        mediaMetadataRetriever = new MediaMetadataRetriever();
////
////        btnOpen.setOnClickListener(new View.OnClickListener(){
////
////            @Override
////            public void onClick(View v) {
////
////                Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
////                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                intent.setType("video/mp4");
////                startActivityForResult(intent, 0);
////            }});
////
////        btnSave.setOnClickListener(new View.OnClickListener(){
////
////            @Override
////            public void onClick(View v) {
////                if(mediaMetadataRetriever != null){
////                    TaskSaveGIF myTaskSaveGIF = new TaskSaveGIF(timeFrameBar);
////                    myTaskSaveGIF.execute();
////                }
////            }});
////
////        timeFrameBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
////
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress,
////                                          boolean fromUser) {}
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {}
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////                updateFrame();
////            }});
////    }
////
////    private void updateFrame(){
////        int frameProgress = timeFrameBar.getProgress();
////
////        long frameTime = maxDur * frameProgress/100;
////
////        textCurDur.setText(String.valueOf(frameTime) + " us");
////        Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(frameTime);
////        capturedImageView.setImageBitmap(bmFrame);
////    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        if(resultCode == RESULT_OK){
////            Uri uri = data.getData();
////            textInfo.setText(uri.toString());
////
////            MediaMetadataRetriever tRetriever = new MediaMetadataRetriever();
////
////            try{
////                tRetriever.setDataSource(getBaseContext(), uri);
////
////                mediaMetadataRetriever = tRetriever;
////                //extract duration in millisecond, as String
////                String DURATION = mediaMetadataRetriever.extractMetadata(
////                        MediaMetadataRetriever.METADATA_KEY_DURATION);
////                textMaxDur.setText(DURATION + " ms");
////                //convert to us, as int
////                maxDur = (long)(1000*Double.parseDouble(DURATION));
////
////                timeFrameBar.setProgress(0);
////                updateFrame();
////            }catch(RuntimeException e){
////                e.printStackTrace();
////                Toast.makeText(MainActivity.this,
////                        "Something Wrong!",
////                        Toast.LENGTH_LONG).show();
////            }
////
////        }
////    }
////
////    public class TaskSaveGIF extends AsyncTask<Void, Integer, String> {
////
////        SeekBar bar;
////
////        public TaskSaveGIF(SeekBar sb){
////            bar = sb;
////            Toast.makeText(MainActivity.this,
////                    "Generate GIF animation",
////                    Toast.LENGTH_LONG).show();
////        }
////
////        @Override
////        protected String doInBackground(Void... params) {
////            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
////            File outFile = new File(extStorageDirectory, "test.GIF");
////            try {
////                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
////                bos.write(genGIF());
////                bos.flush();
////                bos.close();
////
////                return(outFile.getAbsolutePath() + " Saved");
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////                return e.getMessage();
////            } catch (IOException e) {
////                e.printStackTrace();
////                return e.getMessage();
////            }
////        }
////
////        @Override
////        protected void onPostExecute(String result) {
////            Toast.makeText(MainActivity.this,
////                    result,
////                    Toast.LENGTH_LONG).show();
////        }
////
////        @Override
////        protected void onProgressUpdate(Integer... values) {
////            bar.setProgress(values[0]);
////            updateFrame();
////        }
////
////        private byte[] genGIF(){
////            ByteArrayOutputStream bos = new ByteArrayOutputStream();
////
////            AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
////            animatedGifEncoder.setDelay(1000);
////
////            Bitmap bmFrame;
////            animatedGifEncoder.start(bos);
////            for(int i=0; i<100; i+=10){
////                long frameTime = maxDur * i/100;
////                bmFrame = mediaMetadataRetriever.getFrameAtTime(frameTime);
////                animatedGifEncoder.addFrame(bmFrame);
////                publishProgress(i);
////            }
////
////            //last from at end
////            bmFrame = mediaMetadataRetriever.getFrameAtTime(maxDur);
////            animatedGifEncoder.addFrame(bmFrame);
////            publishProgress(100);
////
////            animatedGifEncoder.finish();
////            return bos.toByteArray();
////        }
////    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
////        String strCommand = "ffmpeg -loop 1 -t 3 -i " + /sdcard/videokit/1.jpg + " -loop 1 -t 3 -i " + /sdcard/videokit/2.jpg + " -loop 1 -t 3 -i " + /sdcard/videokit/3.jpg + " -loop 1 -t 3 -i " + /sdcard/videokit/4.jpg + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast " + /sdcard/videolit/output.mp4;
//
//        loadFFMpegBinary();
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setType("video/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                startActivityForResult(Intent.createChooser(intent, "Select Video"), 1);
//            }
//        }, 100);
//    }
//
//    private void loadFFMpegBinary() {
////        if (FFmpeg.getInstance(this).isSupported()) {
////            Intent intent = new Intent();
////            intent.setType("image/*");
////            intent.setAction(Intent.ACTION_PICK);
////            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
////
////            // ffmpeg is supported
////        } else {
////                showUnsupportedExceptionDialog();
////            // ffmpeg is not supported
////        }
//    }
//
//    private void showUnsupportedExceptionDialog() {
//        new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle(getString(R.string.app_name))
//                .setCancelable(false)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                })
//                .create()
//                .show();
//
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == 1) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                Uri selectedImageUri = data.getData();
//
//                // OI FILE Manager
////                String selectedImagePath = selectedImageUri.getPath();
////                String selectedImagePath = FileHelper.getPath(this, selectedImageUri);
//                Uri uri = data.getData();
//                String path = FileHelper.getPath(this, uri);
//
//
//                String output = Utilforbitmap.createNewAttachmentName(".mp4");
//                // MEDIA GALLERY
//
////                String selectedImagePath = getPath(selectedImageUri);
//                String strCommand = "-loop 1 -t 3 -i  " + path + " -loop 1 -t 3 -i  " + path + " -loop 1 -t 3 -i  " + path + " -loop 1 -t 3 -i  " + path + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast " + output;
//                String[] command = strCommand.split(" ");
//
////                conversion(command);
//
////                if (selectedImagePath != null) {
//                    String[] cmd = {"-i"
//                            , path
//                            , output};
//                    conversion(cmd);
//
////                }
//            }
//        }
//    }
//
//    public String getPath(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        if (cursor != null) {
//            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
//            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } else
//            return null;
//    }
//
//    public void conversion(String[] cmd) {
//        FFmpeg ffmpeg = FFmpeg.getInstance(this);
//        // to execute "ffmpeg -version" command you just need to pass "-version"
//        ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {
//
//            @Override
//            public void onStart() {
//                CommonUtils.showToast("start");
//
//            }
//
//            @Override
//            public void onProgress(String message) {}
//
//            @Override
//            public void onFailure(String message) {
//                CommonUtils.showToast(message);
//
//            }
//
//            @Override
//            public void onSuccess(String message) {
//                CommonUtils.showToast(message);
//            }
//
//            @Override
//            public void onFinish() {}
//
//        });
//
//
////        try {
////
////
////            // to execute "ffmpeg -version" command you just need to pass "-version"
////            ffmpeg.execute(cmd, new FFmpegExecuteResponseHandler() {
////                @Override
////                public void onStart() {
////                    CommonUtils.showToast("start");
////                }
////
////                @Override
////                public void onProgress(String message) {
////                }
////
////                @Override
////                public void onFailure(String message) {
////                    CommonUtils.showToast("fail");
////
////                }
////
////                @Override
////
////                public void onSuccess(String message) {
////                    CommonUtils.showToast("success");
////
////                }
////
////                @Override
////                public void onFinish() {
////                }
////            });
////
////        } catch (FFmpegCommandAlreadyRunningException e) {
////            // Handle if FFmpeg is already running
////            CommonUtils.showToast(e.toString());
////
////            e.printStackTrace();
////        }
//    }
}
