package todday.funny.seoulcatcher.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Button;

import java.util.Locale;

public class HeartSpeech {
    private Context context;

    private TextToSpeech tts;

    private Button button;
    private int speakFlag = 1;

    private Bundle bundle = new Bundle();

    public HeartSpeech(Context context) {
        this.context = context;
    }

    public void speakTTS() {
        bundle.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "11");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    speak1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {

                        }

                        @Override
                        public void onDone(String s) {
                            Log.e("REERER", "ERERE");
                            if (speakFlag != 8)
                                speakFlag++;
                            doThread(speakFlag);
                        }

                        @Override
                        public void onError(String s) {

                        }
                    });
                } else {
                    Log.e("text error", "error");
                }


            }
        });
    }

    public void stopTTS() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    public boolean isSpeaking() {
        if (tts != null) {
            return tts.isSpeaking();
        }
        return false;
    }

    private void doThread(int flag) {

        switch (speakFlag) {
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            speak2();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
            case 3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            speak3();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            speak4();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 5:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            speak5();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 6:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            speak6();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 7:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            speak7();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 8:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            speak8();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;


        }

    }

    @SuppressLint("NewApi")
    private void speak1() {
        tts.speak("심폐소생술 음성 가이드를 시작합니다", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }

    @SuppressLint("NewApi")
    private void speak2() {
        tts.speak("환자의 호흡과 의식을 확인하고 주변에 119에 신고해줄 사람이 있다면 신고를 요청하시고 없다면 직접 신고하세요.", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }

    @SuppressLint("NewApi")
    private void speak3() {
        tts.speak("환자를 평평하고 단단한 바닥에 눞힌뒤 , 환자 옆에 무릎을 꿇고 앉습니다", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }

    @SuppressLint("NewApi")
    private void speak4() {
        tts.speak("환자의 가슴 중앙에 한손의 손과 속목의 경계부분을 가져다대시고 다른한손은 그 손위에 깍지를껴서 포개도록합니다 ", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }

    @SuppressLint("NewApi")
    private void speak5() {
        tts.speak("환자의 가슴과 본인의 손 그리고 본인의 어깨가 수직이 되도록 합니다", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }

    @SuppressLint("NewApi")
    private void speak6() {
        tts.speak("이제 본격적인 가슴압박을 시작하겠습니다.", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }

    @SuppressLint("NewApi")
    private void speak7() {
        tts.speak("환자의 가슴을 깊고빠르게 5센치 정도 수직으로 누릅니다. 누른뒤에 가슴이 다시 올라오는지 확인하세요.", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }

    @SuppressLint("NewApi")
    private void speak8() {
        tts.setSpeechRate(1.5f);
        tts.speak("하나. 둘. 셋. 넷. 다섯. 여섯. 일곱. 여덟. 아홉. 열.", TextToSpeech.QUEUE_FLUSH, bundle, "11");
    }


}


