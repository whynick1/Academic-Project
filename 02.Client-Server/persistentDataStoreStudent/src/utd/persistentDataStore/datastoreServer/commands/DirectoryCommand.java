package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DirectoryCommand extends ServerCommand {
	private static Logger logger = Logger.getLogger(DirectoryCommand.class);
	
	@Override
	public void run() throws IOException, ServerException {
		logger.debug("DirectoryCommand Running");
		
		List<String> directory = new ArrayList<String>();
		directory = FileUtil.directory();
		
		this.sendOK();
		
		int length = directory.size();
		StreamUtil.writeLine(length + "\n", outputStream);
		
		for(String s: directory){
			StreamUtil.writeLine(s + "\n", outputStream);
		}
		
		logger.debug("Directory Successfully");
		
	}

}
