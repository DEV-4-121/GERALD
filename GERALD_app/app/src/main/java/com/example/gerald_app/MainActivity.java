<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/blue"
    tools:context=".MainActivity">

    <!--    <TextView-->
    <!--        android:id="@+id/textView2"-->
    <!--        android:layout_width="wrap_content"-->
    <!--        android:layout_height="wrap_content"-->
    <!--        android:layout_margin="1dp"-->
    <!--        android:layout_marginLeft="1dp"-->
    <!--        android:layout_marginTop="1dp"-->
    <!--        android:layout_marginRight="1dp"-->
    <!--        android:layout_marginBottom="8dp"-->
    <!--        android:padding="1dp"-->
    <!--        android:paddingStart="1dp"-->
    <!--        android:paddingLeft="1dp"-->
    <!--        android:paddingTop="1dp"-->
    <!--        android:paddingEnd="1dp"-->
    <!--        android:paddingRight="1dp"-->
    <!--        android:paddingBottom="1dp"-->
    <!--        android:text="Welcome to GERALD Security"-->
    <!--        android:textSize="24sp"-->
    <!--        app:layout_constraintBottom_toTopOf="@+id/btnRun"-->
    <!--        app:layout_constraintHorizontal_bias="0.494"-->
    <!--        app:layout_constraintLeft_toLeftOf="parent"-->
    <!--        app:layout_constraintRight_toRightOf="parent"-->
    <!--        app:layout_constraintTop_toTopOf="parent"-->
    <!--        app:layout_constraintVertical_bias="0.036" />-->

    <TextView
        android:id="@+id/textView3"
        android:layout_width="85dp"
        android:layout_height="27dp"
        android:layout_marginBottom="7dp"
        android:text="Designed by DEV4"
        android:textSize="10sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/imageView" />

    <Button
        android:id="@+id/btnRun"
        android:layout_width="342dp"
        android:layout_height="76dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        android:text="Turn GERALD On"
        app:layout_constraintBottom_toTopOf="@+id/textView3"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.758"
        tools:visibility="visible" />

    <Button
        android:id="@+id/btnStop"
        android:layout_width="342dp"
        android:layout_height="76dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        android:text="Turn GERALD Off"
        app:layout_constraintBottom_toTopOf="@+id/textView3"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.879"
        tools:visibility="visible" />

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="348dp"
        android:layout_height="260dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="117dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="307dp"
        app:layout_constraintBottom_toTopOf="@+id/textView3"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.446"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.64"
        app:srcCompat="@drawable/device" />

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar2"
        android:layout_width="437dp"
        android:layout_height="59dp"
        android:background="?attr/colorPrimary"
        android:minHeight="?attr/actionBarSize"
        android:theme="?attr/actionBarTheme"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.0" />

    <ImageView
        android:id="@+id/imageView4"
        android:layout_width="75dp"
        android:layout_height="56dp"
        app:layout_constraintBottom_toTopOf="@+id/imgCapture"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/toolbar2"
        app:layout_constraintVertical_bias="0.333"
        app:srcCompat="@drawable/GERALD" />


</androidx.constraintlayout.widget.ConstraintLayout>
// REFERENCES
// For GET/POST methods with flask API: https://varunmishra.com/teaching/cs65/http-volley/
// More references for server communication: https://medium.com/@manuaravindpta/networking-using-volley-library-39c22061b4ba
// multithreading for check detection: https://stackoverflow.com/questions/3489543/how-to-call-a-method-with-a-separate-thread-in-java
// time sleep for thread: https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
