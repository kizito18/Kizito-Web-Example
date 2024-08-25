package com.my.zitos.multibroser.kizitowebexample.sharedPref

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object AllSerializer : Serializer<AllPref> {
    override val defaultValue: AllPref
        get() = AllPref()

    override suspend fun readFrom(input: InputStream): AllPref {

        return try {

            Json.decodeFromString(
                deserializer = AllPref.serializer(),
                string = input.readBytes().decodeToString()

            )

        }catch (e: SerializationException){

            e.printStackTrace()
            defaultValue

        }


    }

    override suspend fun writeTo(t: AllPref, output: OutputStream) {

        withContext(Dispatchers.IO) {

            output.write(

                Json.encodeToString(

                    serializer = AllPref.serializer(),
                    value = t
                ).encodeToByteArray()

            )
        }


    }


}
