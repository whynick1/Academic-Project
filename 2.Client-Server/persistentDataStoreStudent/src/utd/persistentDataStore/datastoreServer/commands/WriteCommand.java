package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand {
	private static Logger logger = Logger.getLogger(WriteCommand.class);
	
	@Override
	public void run() throws IOException, ServerException {
		logger.debug("WriteCommand Running");
		
		String readName = StreamUtil.readLine(inputStream);
		
		String dataSize = StreamUtil.readLine(inputStream);
		int dataLength = Integer.parseInt(dataSize);
		
		byte[] data = StreamUtil.readData(dataLength, inputStream);
				
		FileUtil.writeData(readName, data);
		
		this.sendOK();
		
		logger.debug("Write Successfully");		
	}

}
