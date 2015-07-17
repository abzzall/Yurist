package shivas.studio.com.yurist;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class RSSParserClass {

    private List<String> titles;
    private List<String> links;
    private List<String> descriptions;

    boolean ready = false;

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    public RSSParserClass(String url) {
        this.urlString = url;

        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        descriptions = new ArrayList<String>();
    }

    public List<String> getTitle() {
        return titles;
    }

    public List<String> getLink() {
        return links;
    }

    public List<String> getDescription() {
        return descriptions;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        boolean read = false;
        String text=null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT && (titles.size() < 30 || descriptions.size() < 30 || links.size() < 30)) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(name.equals("content:encoded")) {
                            myParser.nextTag();
                        }
                        if(name.equals("item")) {
                            read = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if(read) {
                            text = myParser.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(read) {
                            if(name.equals("title")){
                                titles.add(text);
                            }
                            else if(name.equals("link")){
                                links.add(text);
                            }
                            else if(name.equals("description")){
                                descriptions.add(text);
                            } else if(name.equals("item")) {
                                read = false;
                            } else {
                            }
                        }
                        break;
                }
                event = myParser.next();
            }

            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(1000 /* milliseconds */);
                    conn.setConnectTimeout(1500 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                }
            }
        });
        thread.start();
    }
}
