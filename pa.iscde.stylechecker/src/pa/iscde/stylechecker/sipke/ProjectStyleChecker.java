package pa.iscde.stylechecker.sipke;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ProjectStyleChecker {

	private ASTVisitor visitor;


	public ProjectStyleChecker(ASTVisitor visitor) {
		this.setVisitor(visitor);
	}
	
	public void checkWorkSpace() {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = workspaceRoot.getProjects();
		for (IProject project : projects) {
			if(isJavaProject(project))
				checkJavaProject(project);
		}
	}

	private boolean isJavaProject(IProject project) {
		try {
			return project.isNatureEnabled(JavaCore.NATURE_ID);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void checkJavaProject(IProject project) {
		IJavaProject javaProject = JavaCore.create(project);
		try {
			IPackageFragment[] allPackages = javaProject.getPackageFragments();
			for (IPackageFragment iPackage : allPackages) {
				if(iPackage.getKind()==IPackageFragmentRoot.K_SOURCE)
					checkPackage(iPackage.getCompilationUnits());
			}
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	private void checkPackage(ICompilationUnit[] compilationUnits) {
		for (ICompilationUnit compUnit : compilationUnits) {
			 checkJavaFile(compUnit);

		}
	}

	private void checkJavaFile(ICompilationUnit compUnit) {
		CompilationUnit parse = parse(compUnit);
		  parse.accept(getVisitor());
	}
	
	
	private static CompilationUnit parse(ICompilationUnit unit) {
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(unit);
	    parser.setResolveBindings(true);
	    return (CompilationUnit) parser.createAST(null);
	  }

	public ASTVisitor getVisitor() {
		return visitor;
	}

	public void setVisitor(ASTVisitor visitor) {
		this.visitor = visitor;
	}

}
