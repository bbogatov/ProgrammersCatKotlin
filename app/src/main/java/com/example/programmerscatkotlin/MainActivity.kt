package com.example.programmerscatkotlin

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.io.InputStream
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val poem = readPoem(this, "poem.txt")

        val poemName = getPoemName(poem)
        val poemNameView: TextView = findViewById(R.id.poem_name)
        poemNameView.text = poemName

        val poemText: String = getPoemText(poem)
        val poemTextView: TextView = findViewById(R.id.poem_text)
        poemTextView.text = poemText
    }


    /**
     * Method returns all text of poem. Actually it returns all text after first paragraph.
     *
     * @param poem poem that where first paragraph means poem name, other is poems text
     * @return returns all text after first paragraph
     */
    private fun getPoemText(poem: String): String {

        lateinit var poemText: String

        val chars: CharArray = poem.toCharArray()

        for ((index, value) in chars.withIndex()) {

            if (value == '\n') {
                val charArray = CharArray(chars.size - index + 1) //+1 it means do not include '\n' char

                //+1 and -1 means do not include '\n' char
                System.arraycopy(chars, index + 1, charArray, 0, chars.size - index - 1)

                poemText = String(charArray)
                break
            }
        }

        return poemText
    }


    /**
     * Method returns first paragraph from inputted text. Returns paragraph will be poem name.
     *
     * @param poemText full poem text, where first paragraph must be poems name
     * @return returns first paragraph
     */
    private fun getPoemName(poemText: String): String {

        val stringBuilder = StringBuilder()


        for (value in poemText.toCharArray()) {
            if (value == '\n') {
                break
            } else {
                stringBuilder.append(value)
            }
        }

        return stringBuilder.toString()
    }

    /**
     * Method that reads poem from file that situated in assets folder, and returns its text as a string.
     *
     *
     * @param activity current activity
     * @param poem poem name it must be txt file that situated in 'assets' folder
     * @return returns full poem text, where first paragraph means poems name other text poem
     */
    private fun readPoem(activity: Activity, poem: String): String {

        val stream: InputStream = activity.assets.open(poem)

        val bytes: ArrayList<Byte> = ArrayList()

        var currentByte: Int = stream.read()

        while (currentByte > -1) {
            bytes.add(currentByte.toByte())
            currentByte = stream.read()
        }

        val poemText = ByteArray(bytes.size)

        for ((index, element) in bytes.withIndex()) {
            poemText[index] = element
        }

        return String(poemText)
    }
}
