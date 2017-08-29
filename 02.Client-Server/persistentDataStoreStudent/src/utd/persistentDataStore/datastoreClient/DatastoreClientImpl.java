package utd.persistentDataStore.datastoreClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.StreamUtil;

public class DatastoreClientImpl implements DatastoreClient
{
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	private InetAddress address;
	private int port;

	public DatastoreClientImpl(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
	}
	
	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.String, byte[])
	 */
	@Override
    public void write(String name, byte data[]) throws ClientException
	{
		logger.debug("Executing Write Operation");
		Socket socket;
		OutputStream out;
		InputStream in;
		String operation;
		
		name += "\n";
		int sizeNum = data.length;
		String size = sizeNum +"\n";
		
		try {
			// request connection with server
			socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			
			
			out = socket.getOutputStream();
			// write Writecommand to output stream as the protocol format
			// provided by professor
			StreamUtil.writeLine("write\n", out);
			StreamUtil.writeLine(name, out);
			StreamUtil.writeLine(size, out);
			StreamUtil.writeData(data, out);

			in = socket.getInputStream();
			// read respond info from server
			operation = StreamUtil.readLine(in);
			StreamUtil.closeSocket(in);
			
			if (operation.equalsIgnoreCase("ok")){
				logger.debug("Write successfully");
			}else{
				logger.debug("fail to write");
			}
			
			socket.close();
			
		} catch (IOException e) {
			logger.error(e);
		}

	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.String)
	 */
	@Override
    public byte[] read(String name) throws ClientException
	{
		logger.debug("Executing Read Operation");
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		String result = null;
		byte[] data = null;
		
		name = name + "\n";
		
		try {
			// request connection with server
			socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			
			out = socket.getOutputStream();
			// write Readcommand to output stream as the protocol format
			// provided by professor
			StreamUtil.writeLine("read\n", out);
			StreamUtil.writeLine(name, out);
			
			in = socket.getInputStream();
			// read respond info from server
			result = StreamUtil.readLine(in);
			// if succeed
			if (result.equalsIgnoreCase("ok")) {
				String length = StreamUtil.readLine(in);
				int lengthNum = Integer.parseInt(length);
				// retrieve data from Server
				data = StreamUtil.readData(lengthNum, in);
				StreamUtil.closeSocket(in);
				socket.close();
				logger.debug("Read Successfully");
			// if fail
			}else {	
					StreamUtil.closeSocket(in);
					socket.close();
					throw new ClientException("No file with name of " + name + result);
			}
			
		} catch (IOException e) {
			logger.error(e);
		}

		return data;
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.String)
	 */
	@Override
    public void delete(String name) throws ClientException
	{
		logger.debug("Executing Delete Operation");
		Socket socket = new Socket();
		
		OutputStream out = null;
		InputStream in = null;
		name = name + "\n";
		String result = null;

		try {
			// request connection with server
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			out = socket.getOutputStream();
			// write Deletecommand to output stream as the protocol format
			// provided by professor
			StreamUtil.writeLine("delete\n", out);
			StreamUtil.writeLine(name, out);
			
			in = socket.getInputStream();
			// read respond info from server
			result = StreamUtil.readLine(in);
			StreamUtil.closeSocket(in);
			
			// if succeed
			if (result.equalsIgnoreCase("ok")){
				logger.debug("Delete Successfully");
				socket.close();
			// if fail
			}else {    
				logger.debug("Fail to delete");
				socket.close();
				throw new ClientException("Can't delete the file with name of "	+ name + ".");
			}

		} catch (IOException e) {		
			logger.error(e);
			throw new ClientException("IO Exception" + e);
		}
		
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
	 */
	@Override
    public List<String> directory() throws ClientException
	{
		logger.debug("Executing Directory Operation");
		List<String> list = new ArrayList<>();
		
		OutputStream out = null;
		InputStream in = null;
		String result = null;
		int length = 0;
		try {
			// request connection with server
			SocketAddress saddr = new InetSocketAddress(address, port);
			Socket socket = new Socket();
			socket.connect(saddr);
			out = socket.getOutputStream();
			// write Listcommand to output stream as the protocol format
			// provided by professor
			StreamUtil.writeLine("directory\n", out);

			in = socket.getInputStream();
			// read respond from server about Listcommand
			result = StreamUtil.readLine(in);
			// any file exist
			if (result.equalsIgnoreCase("ok")) {
				String strLength = StreamUtil.readLine(in);
				length = Integer.parseInt(strLength);   
				String line = null;
				// print all files' name
				while (length > 0) {       
					line = StreamUtil.readLine(in);    
					list.add(line);
					length--;
				}
				StreamUtil.closeSocket(in);
				socket.close();
			// no file exits
			} else {      
				StreamUtil.closeSocket(in);
				socket.close();
				throw new ClientException("response");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		
		return list;
	}

}
