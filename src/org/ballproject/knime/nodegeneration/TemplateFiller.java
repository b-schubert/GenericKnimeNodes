package org.ballproject.knime.nodegeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TemplateFiller
{
	private String data;
	
	public void read(String file) throws IOException
	{
		read(new File(file));
	}
	
	public void read(File file) throws IOException
	{
		FileInputStream in = new FileInputStream(file);
		read(in);
		in.close();
	}
	
	public void read(InputStream in) throws IOException
	{
		StringBuffer buf = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while( (line=reader.readLine())!=null )
		{
			buf.append(line+System.getProperty("line.separator"));
		}
		data = buf.toString();
	}
	
	public void replace(String token, String value)
	{
		data = data.replace(token,value);
	}
	
	public void write(OutputStream out) throws IOException
	{
		out.write(data.getBytes());
	}
	
	public void write(File file) throws IOException
	{
		file.getParentFile().mkdirs();
		FileOutputStream out = new FileOutputStream(file);
		write(out);
		out.close();
	}
	
	public void write(String file) throws IOException
	{
		write(new File(file));
	}

	

	public static void main(String[] args) throws IOException
	{
		TemplateFiller tf = new TemplateFiller();
		tf.read("/tmp/templ");
		tf.replace("__NAME__", "Marc");
		tf.write("/tmp/raus");
	}

}