package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MyView extends View {
    private int xWidth;  // ancho del lienzo de dibujo
    private int yHeight; // alto del lienzo de dibujo
    private float newX;  // nueva posición de la nave
    private int sensibilidad; // sensibilidad del acelerometro
    private float xNave, yNave;  // posición de la nave
    private float xNI, yNI; // posición de las naves invasoras
    private float xBala, yBala; // posición de la bala o proyectil
    private float anchoNave, altoNave; // ancho y alto de la imagen de la nave
    private float anchoNI, altoNI; // ancho y alto de la imagen de nave invasora
    private float separacion; // separacion entre naves invasoras
    private int numNaves; // número de naves invasoras a dibujar
    private boolean dispara; // para indicar que se realizó un disparo
    boolean [] dibuja;       // arreglo de banderas para dibujar la nave invasora

    // archivos para graficos
    Bitmap bmpBackground;
    Bitmap bmpNave;
    Bitmap bmpNI;
    Bitmap bmpBala;
    Bitmap bmpExp;
    // archivos para sonido
    private MediaPlayer shot;
    private MediaPlayer boom;

    public MyView(Context context) {
        super(context);
        sensibilidad = 200;
        dispara      = false;
        numNaves     = 5;
        separacion   = 60;

        // Recursos de imágenes
        bmpBackground = BitmapFactory.decodeResource(
                        getResources(),R.drawable.universe2);
        bmpNave       = BitmapFactory.decodeResource(
                        getResources(),R.drawable.spaceship2);
        bmpNI         = BitmapFactory.decodeResource(
                        getResources(),R.drawable.badship2);
        bmpBala       = BitmapFactory.decodeResource(
                        getResources(),R.drawable.shot);
        bmpExp        = BitmapFactory.decodeResource(
                        getResources(), R.drawable.explosion);
        //recursos de sonido
        shot = MediaPlayer.create(context,R.raw.disparo);
        boom = MediaPlayer.create(context,R.raw.explosion);

        //  obtener dimensiones de las naves
        anchoNave = bmpNave.getWidth();
        altoNave  = bmpNave.getHeight();

        anchoNI = bmpNI.getWidth();
        altoNI  = bmpNI.getHeight();

        // Valores iniciales para las posiciones de:
        // Nave invasora
        xNI = 0;
        yNI = 50;

        // proyectil
        xBala = 0;
        yBala = 0;

        // arreglo de banderas para dibujar NI
        dibuja = new  boolean[30];
        for(int i = 0;i< dibuja.length;i++)
            dibuja[i] = true;
    } // termina constructor

    // Método para dibujar el escenario 2D


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // obtener el alto y ancho del lienzo (pantalla del celular)
        xWidth = getWidth();
        yHeight = getHeight();

        // posición de la nave ( en el universo )
        xNave = xWidth/2 -anchoNave/2 -sensibilidad*newX;
        yNave = yHeight - altoNave;

        // se establece el fondo
        canvas.drawBitmap(bmpBackground,0,0,null);

        // colocar la nave defensora
        canvas.drawBitmap(bmpNave,xNave,yNave, null);

        //colocar las naves invasoras
        numNaves = (int)(xWidth/anchoNI);
        xNI = 0;
        for(int i = 1; i <= numNaves; i++){
            if(dibuja[i])
                canvas.drawBitmap(bmpNI,xNI,yNI,null);
            xNI = xNI + anchoNI + separacion;
        }

        // disparo del proyectil
        // colocar el proyectil con respecto a la nave que lo lanza
        if(!dispara){
            yBala = yNave;
            xBala = xNave + anchoNave/2;
        }

        // dibujar el proyectil
        if(dispara){
            yBala -=20;
            canvas.drawBitmap(bmpBala,xBala,yBala,null);
        }
        // aquí se revisa la colisión de la bala con la nave invasora

        // preparar para el siguiente disparo
        if(yBala < yNI + altoNI) {
            dispara = false;
            boom.start();
            canvas.drawBitmap(bmpExp, xBala-anchoNI/2, yBala-altoNI/2, null);

        }


        // repintar el escenario
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dispara = true;
        shot.start();
        return true;
    }

    public void actualizaX(float x){
        newX = x;
    }

    public void destroyNI(float bala) { }
}
