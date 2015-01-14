package pa.iscde.stylechecker.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.SortedSet;

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
import org.eclipse.jdt.core.dom.CompilationUnit;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class ProjectStyleChecker {

	private StyleCheckerASTVisitor visitor;


	public ProjectStyleChecker(StyleCheckerASTVisitor visitor) {
		this.visitor=visitor;
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
	
	public void checkRootPackage(PackageElement root){
		SortedSet<SourceElement> childrens = root.getChildren();
		for (SourceElement element : childrens) {
			if(element.isClass()){
				CompilationUnit cu = parse(readSource(element.getFile()));
				cu.accept(visitor);
			}else {
				if(element.isPackage())
					checkRootPackage((PackageElement) element);
			}
		}
	}
	
	private String readSource(File file) {
		StringBuilder src = new StringBuilder();
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine())
				src.append(scanner.nextLine()).append('\n');
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return src.toString();
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
	

	
	
	private  CompilationUnit parse(ICompilationUnit unit) {
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(unit);
	    parser.setResolveBindings(true);
	    return (CompilationUnit) parser.createAST(null);
	  }
	
	private  CompilationUnit parse(String fileContent) {
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(fileContent.toCharArray());
	    parser.setResolveBindings(true);
	    return (CompilationUnit) parser.createAST(null);
	  }

	public StyleCheckerASTVisitor getVisitor() {
		return visitor;
	}

}
