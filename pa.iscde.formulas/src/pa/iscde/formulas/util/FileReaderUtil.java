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
			//		Isto da todos os ficheiros daquela pastas		 
			IResource[] members = folder.members();
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
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return allFormulas;

	}
}
