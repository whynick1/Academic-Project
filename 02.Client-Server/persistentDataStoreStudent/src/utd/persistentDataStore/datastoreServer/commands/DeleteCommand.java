package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DeleteCommand extends ServerCommand {
	private static Logger logger = Logger.getLogger(DeleteCommand.class);
	
	@Override
	public void run() throws IOException, ServerException {
		logger.debug("DeleteCommand Running");
		
		String readName = StreamUtil.readLine(inputStream);
		
		if(FileUtil.deleteData(readName)){
			this.sendOK();
			logger.debug("Delete Successfully");
		}else{
			this.sendError("Unknown file");
			throw new ServerException("File deletion failed");
		}
		
	}

}
