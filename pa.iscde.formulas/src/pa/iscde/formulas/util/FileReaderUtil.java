package pa.iscde.formulas.util;

import java.io.InputStream;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class FileReaderUtil {

	public static String readFile(){
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject project  = root.getProject("Teste");
		IFolder folder = project.getFolder("formulas");
		String allFormulas = "";
		
		try{
			if (!project.exists()) project.create(null);
			if (!project.isOpen()) project.open(null);
			if (!folder.exists()) 
				folder.create(IResource.NONE, true, null);
			//		Isto da todos os ficheiros daquela pastas	
			
			IResource[] members = folder.members();
			if(members.length!=0){
			for (int i = 0; i < members.length; i++) {
				IFile f = folder.getFile(members[i].getName());
				InputStream is =f.getContents();
				Scanner s = new Scanner(is, "UTF-8");
				while(s.hasNext()){
					allFormulas += s.nextLine();
				}
				allFormulas+="END";
				s.close();
			}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return allFormulas;

	}
}
