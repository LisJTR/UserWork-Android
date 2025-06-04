package com.torre.b2c2c_tfg.NoticiaRss

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.net.URL

suspend fun leerRssDesdeUrl(urlString: String): List<NoticiaRss> = withContext(Dispatchers.IO) {
    val noticias = mutableListOf<NoticiaRss>()
    try {
        val url = URL(urlString)
        val inputStream = url.openConnection().apply {
            setRequestProperty("User-Agent", "Mozilla/5.0")
        }.getInputStream()

        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(InputStreamReader(inputStream))

        var eventType = parser.eventType
        var tagName: String?
        var isAtom = false
        var titulo = ""
        var descripcion = ""
        var fecha = ""
        var link = ""

        while (eventType != XmlPullParser.END_DOCUMENT) {
            tagName = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (tagName.equals("feed", ignoreCase = true)) {
                        isAtom = true
                    }

                    if (!isAtom) {
                        when (tagName) {
                            "title" -> titulo = parser.nextText()
                            "description" -> descripcion = parser.nextText()
                            "pubDate" -> fecha = parser.nextText()
                            "link" -> link = parser.nextText()
                        }
                    } else {
                        when (tagName) {
                            "title" -> titulo = parser.nextText()
                            "summary" -> descripcion = parser.nextText()
                            "published" -> fecha = parser.nextText()
                            "link" -> {
                                val rel = parser.getAttributeValue(null, "rel")
                                val type = parser.getAttributeValue(null, "type")
                                val href = parser.getAttributeValue(null, "href")

                                if (rel == "alternate" && type == "text/html" && !href.isNullOrEmpty()) {
                                    link = href
                                }
                            }
                        }
                    }
                }

                XmlPullParser.END_TAG -> {
                    val endTag = parser.name
                    if ((!isAtom && endTag == "item") || (isAtom && endTag == "entry")) {
                        noticias.add(NoticiaRss(titulo, descripcion, fecha, link))
                        titulo = ""
                        descripcion = ""
                        fecha = ""
                        link = ""
                    }
                }
            }
            eventType = parser.next()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
    noticias
}
