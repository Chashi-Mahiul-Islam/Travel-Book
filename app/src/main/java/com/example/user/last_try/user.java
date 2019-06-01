package com.example.user.last_try;

/**
 * Created by User on 10/16/2017.
 */

class user {

    public String username;
    public String password;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user(String username, String password) {
        this.username = username;
        this.password = password;
    }


}/*

 <EditText
        android:id="@+id/search_word"
        android:layout_width="376dp"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:background="@color/cardview_light_background"
        android:hint="add a country to search"
        android:text=""
        android:textColor="@color/colorPrimary"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.0"
        tools:layout_editor_absoluteX="8dp"
        tools:layout_editor_absoluteY="32dp" />
*/
