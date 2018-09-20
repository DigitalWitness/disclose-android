package com.gtri.icl.nij.disclose.API;

import org.json.JSONObject;
import org.json.JSONException;

import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class APIManager
{
    public static final String kBaseUrl = "http://nij-disclose-stsd.gtri.gatech.edu/api";

    public static APIResponse postRequest( String endPoint, JSONObject httpBody )
    {
        return sendRequest( "POST", endPoint, httpBody );
    }

    public static APIResponse sendRequest( String requestMethod, String endPoint, JSONObject httpBody )
    {
        try
        {
            URL url = new URL( kBaseUrl + endPoint );

            // create connection

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod( requestMethod );
            httpURLConnection.setRequestProperty( "Content-Type", "application/json" );

            httpURLConnection.setDoInput( true );
            httpURLConnection.setDoOutput( true );
            httpURLConnection.setUseCaches( false );

            if (httpBody != null)
            {
                DataOutputStream dataOutputStream = new DataOutputStream( httpURLConnection.getOutputStream() );

                dataOutputStream.writeBytes( httpBody.toString() );
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            // validate response

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode != 200)
            {
                String errorMessage = url + " failed.  Invalid response code: " + Integer.toString( responseCode );

                return new APIResponse( false, errorMessage, null );
            }
            else
            {
                // get response body

                String line;
                StringBuilder response = new StringBuilder();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ));

                while ((line = bufferedReader.readLine()) != null)
                {
                    response.append( line );
                    response.append( '\r' );
                }

                bufferedReader.close();

                JSONObject jsonObject;

                // validate response body

                if (response.length() == 0)
                {
                    return new APIResponse( false, url + " failed.  No response.", null );
                }
                else
                {
                    try
                    {
                        jsonObject = new JSONObject( response.toString() );

                        if (jsonObject.getBoolean( "success" ) == true)
                        {
                            return new APIResponse( true, "", jsonObject );
                        }
                        else
                        {
                            return new APIResponse( false, "Invalid Response: " + jsonObject.toString(), jsonObject );
                        }
                    }
                    catch (JSONException e)
                    {
                        return new APIResponse( false, url + " failed.  Invalid response.\n" + response.toString(), null );
                    }
                }
            }

        } catch( Exception e )
        {
            e.printStackTrace();
            return new APIResponse( false, endPoint + " failed.  A fatal exception occurred.\n" + e.toString(), null );
        }
    }
}
