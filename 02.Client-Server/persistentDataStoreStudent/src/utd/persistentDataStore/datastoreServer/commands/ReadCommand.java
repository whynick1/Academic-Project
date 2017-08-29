package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class ReadCommand extends ServerCommand{
	private static Logger logger = Logger.getLogger(ReadCommand.class);
	
	@Override
	public void run() throws IOException, ServerException {
		logger.debug("ReadCommand Running");
		
		String readName = StreamUtil.readLine(inputStream);
		
		byte[] data = null;
		data = FileUtil.readData(readName);
		int dataLength = data.length;
		
		if(dataLength != 0){
			this.sendOK();
			String length = dataLength + "\n";
			StreamUtil.writeLine(length, outputStream);
			StreamUtil.writeData(data, outputStream);
			logger.debug("Read Successfully");
		}else{
			this.sendError("wrong file name\n");
			logger.debug("Fail to read ");
			throw new ServerException("Fail to read");
		}
	}
}
