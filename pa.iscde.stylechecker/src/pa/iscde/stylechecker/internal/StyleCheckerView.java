package pa.iscde.stylechecker.internal;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.stylechecker.domain.Constant;
import pa.iscde.stylechecker.domain.ImportDeclarationRuleExtentisionsProvider;
import pa.iscde.stylechecker.domain.ProjectStyleChecker;
import pa.iscde.stylechecker.domain.StyleCheckerASTVisitor;
import pa.iscde.stylechecker.domain.TryStamentRuleExtensionsProvider;
import pa.iscde.stylechecker.domain.VariableDeclarationRuleExtentisionsProvider;
import pa.iscde.stylechecker.extensibility.AbstractTryStatementRule;
import pa.iscde.stylechecker.extensibility.AbstractVariableDeclarationRule;
import pa.iscde.stylechecker.internal.rules.AbstractImportDeclarationRule;
import pa.iscde.stylechecker.model.AbstractStyleRule;
import pa.iscde.stylechecker.utils.SWTResourceManager;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.internal.JavaEditorActivator;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;




public class StyleCheckerView  implements PidescoView {
	
	
	private  List<AbstractImportDeclarationRule> importStatementRules;
	private  List<AbstractTryStatementRule> tryStatementRules;
	private  List<AbstractVariableDeclarationRule> variableStatementRules;
	private  ProjectStyleChecker checker;
	private static JavaEditorServices editorServices;
	private Button btnRefresh;
	private Button btnClear;
	private ProjectBrowserServices browser;
	private Table tbRules;


	public StyleCheckerView() {	}
	
	private void  initButtons(Composite viewArea) {
	
	//========================Buttons
	Group btnsGroup_tools = new Group(viewArea, SWT.NONE);
	btnsGroup_tools.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
	GridData gd_btnsGroup_tools = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
	gd_btnsGroup_tools.heightHint = 18;
	btnsGroup_tools.setLayoutData(gd_btnsGroup_tools);
	
	btnRefresh = new Button(btnsGroup_tools, SWT.NONE);
	btnRefresh.setBounds(10, 0, 95, 28);
	btnRefresh.setText(Constant.BTN_REFRESH_LABEL);
	
	btnClear = new Button(btnsGroup_tools, SWT.NONE);
	btnClear.setBounds(111, 0, 95, 28);
	btnClear.setText(Constant.BTN_CLEAR_LABEL);
	
	viewArea.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 16, SWT.NORMAL));
	viewArea.setLayout(new GridLayout(1, false));

	//========================Table

	tbRules = new Table(viewArea, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
	tbRules.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
	tbRules.setDragDetect(false);
	tbRules.setSelection(0);
	tbRules.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	tbRules.setHeaderVisible(true);
	tbRules.setLinesVisible(true);
	
	
	TableColumn tblclmnType = new TableColumn(tbRules, SWT.NONE);
	tblclmnType.setWidth(100);
	tblclmnType.setText(Constant.TB_COL_RULE_TYPE_LABEL);
	
	TableColumn tblclmnName = new TableColumn(tbRules, SWT.NONE);
	tblclmnName.setWidth(100);
	tblclmnName.setText(Constant.TB_COL_RULE_DESC_LABEL);
	
	TableColumn tblclmnViolactions = new TableColumn(tbRules, SWT.LEFT);
	tblclmnViolactions.setMoveable(true);
	tblclmnViolactions.setWidth(100);
	tblclmnViolactions.setText(Constant.TB_COL_RULE_VIOLATIONS_LABEL);
	
	//========================Event Listeners 

	btnRefresh.addSelectionListener(new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			File openedFile = editorServices.getOpenedFile();
			clearWarnings();
			resetRulesViolationCounter();
			checkWorkspace(openedFile);
			packAll();
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {	
		}
	});
	
	btnClear.addSelectionListener(new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			clearWarnings();

		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
	});
	
	tbRules.addListener(SWT.Selection, new Listener() {
		
		@Override
		public void handleEvent(Event event) {
			if(event.detail==SWT.CHECK) {
					TableItem[] items = tbRules.getItems();
					if(items!= null && items[event.index].getChecked()) {
						((AbstractStyleRule)items[event.index].getData()).setActive(true);
					}else {
						((AbstractStyleRule)items[event.index].getData()).setActive(false);
					}
			}
		}
	});
	
	}
	
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
			initButtons(viewArea);
			
			
			checker = new ProjectStyleChecker(new StyleCheckerASTVisitor());
			
			BundleContext context = JavaEditorActivator.getInstance().getContext();
			ServiceReference<ProjectBrowserServices> ref2 = context.getServiceReference(ProjectBrowserServices.class);
			
			browser = context.getService(ref2);
			
			importStatementRules = ImportDeclarationRuleExtentisionsProvider.getExtentions();
			importStatementRules.addAll(ImportDeclarationRuleExtentisionsProvider.getInternalRules());
			
			variableStatementRules = VariableDeclarationRuleExtentisionsProvider.getExtentions();
			variableStatementRules.addAll(VariableDeclarationRuleExtentisionsProvider.getInternalRules());
			
			tryStatementRules = TryStamentRuleExtensionsProvider.getExtentions();
			tryStatementRules.addAll(TryStamentRuleExtensionsProvider.getInternalRules());
			checker.checkRootPackage(browser.getRootPackage());
			
			editorServices = JavaEditorActivator.getInstance().getServices();
			editorServices.addListener( new JavaEditorListener() {
				
				@Override
				public void fileSaved(File file) {
					//TODO check with system engineer if is possible to make this feature work 
					resetRulesViolationCounter();
					checkWorkspace(editorServices.getOpenedFile());	
					packAll();
				}
				
				@Override
				public void fileOpened(File file) {	}
				@Override
				public void fileClosed(File file) {	}
				@Override
				public void selectionChanged(File file, String text,int offset, int length) {	}
			});
			
			checkWorkspace(editorServices.getOpenedFile());
			addRules();			
	}
	
	/**
	 * load all system style rules 
	 */
	private void addRules() {

		for (AbstractImportDeclarationRule rule : importStatementRules) {
			addRule(rule);;
		}
		for (AbstractVariableDeclarationRule rule : variableStatementRules) {
			addRule(rule);;
		}
		for (AbstractTryStatementRule rule : tryStatementRules) {
			addRule(rule);;
		}
		
	}
	
	/**
	 *  this method is to responsible for checking the workspace's projects violations and set warnings. 
	 * @param file - current opened file to be updated with warnings 
	 */
	private void checkWorkspace(File file) {
		StyleCheckerASTVisitor visitor = checker.getVisitor();
		visitor.reset();
		checker.checkRootPackage(browser.getRootPackage());
		StyleCheckerASTVisitor openedFileVisitor = new StyleCheckerASTVisitor();
		if(file!=null)
			editorServices.parseFile(file, openedFileVisitor);
		List<ImportDeclaration> importDeclarations = visitor.getImportDeclarations();
		for (ImportDeclaration importDeclaration : importDeclarations) {
			for (AbstractImportDeclarationRule importRule : importStatementRules) {
				if(importRule.getActive() && importRule.check(importDeclaration)) {
					if( isInTheRuleList(openedFileVisitor.getImportDeclarations(),importDeclaration)) {
					editorServices.addAnnotation(file, AnnotationType.WARNING,importRule.getWarningMessage(), 
							importDeclaration.getStartPosition(), importDeclaration.getLength());
				}
					importRule.setViolations(importRule.getViolations()+1);
				}
			}
		}
		
		List<VariableDeclarationStatement> varDeclartions = visitor.getVriableDeclarationStatements();
		for (VariableDeclarationStatement varDeclaration : varDeclartions) {
			for (AbstractVariableDeclarationRule varDeclRule : variableStatementRules) {
				if(varDeclRule.getActive() && varDeclRule.check(varDeclaration)) {
					if( isInTheRuleList(openedFileVisitor.getVriableDeclarationStatements(), varDeclaration)) {
						editorServices.addAnnotation(file, AnnotationType.WARNING,varDeclRule.getWarningMessage(), 
								varDeclaration.getStartPosition(), varDeclaration.getLength());
					}
					varDeclRule.setViolations(varDeclRule.getViolations()+1);
				}
			}
		}
		
		List<TryStatement> tryDeclartions = visitor.getTryStatements();
		for (TryStatement tryDeclaration : tryDeclartions) {
			for (AbstractTryStatementRule tryDeclRule : tryStatementRules) {
				if(tryDeclRule.getActive() && tryDeclRule.check(tryDeclaration)) {
					if( isInTheRuleList(openedFileVisitor.getTryStatements(), tryDeclaration)) {
						editorServices.addAnnotation(file, AnnotationType.WARNING,tryDeclRule.getWarningMessage(), 
								tryDeclaration.getStartPosition(), tryDeclaration.getLength());
					}
					tryDeclRule.setViolations(tryDeclRule.getViolations()+1);
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param list - list of ASTNode
	 * @param node - Current ASTNode
	 * @return true if list contains node otherwise false
	 */
	private boolean isInTheRuleList(List<? extends ASTNode> list,
			ASTNode node) {
		for (ASTNode n : list) {
			if(n.toString().equals(node.toString()) && n.getStartPosition()==node.getStartPosition())
				return true;
		}
		return false;
	}

	/**
	 * reset the rules violations counters 
	 */
	private void resetRulesViolationCounter() {
		
		TableItem[] items = tbRules.getItems();
		for (int i = 0; i < items.length; i++) {
			((AbstractStyleRule)items[i].getData()).setViolations(0);
			items[i].setText(Constant.TB_ITEN_RULE_VIOLATIONS_IDX, "0");
		}
	}
	
	/**
	 *  add a given rule to the plugin view  
	 * @param rule - given rule
	 */
	public void addRule(AbstractStyleRule rule) {
		
		TableItem item = new TableItem(tbRules, SWT.NONE);
		item.setText(Constant.TB_ITEN_RULE_NAME_IDX, rule.getClass().getSimpleName());
		item.setText(Constant.TB_ITEN_RULE_DESC_IDX, rule.getDescription());
		item.setText(Constant.TB_ITEN_RULE_VIOLATIONS_IDX, ""+rule.getViolations());
		item.setData(rule);
		packAll();
		tbRules.computeSize(SWT.FILL, SWT.FILL);
	}
	
	// render calculations
	private void packAll() {
		TableItem[] items = tbRules.getItems();
		for (int i = 0; i < items.length; i++) {
			boolean active = ((AbstractStyleRule)items[i].getData()).getActive();
			items[i].setChecked(active);
			items[i].setText(Constant.TB_ITEN_RULE_VIOLATIONS_IDX, ((AbstractStyleRule)items[i].getData()).getViolations()+"");
		}
		for (int i = 0; i < Constant.TABLE_VIEW_NUM_COLUMNS; i++) {
	        tbRules.getColumn(i).pack();
	      }
		tbRules.update();
	}
	
	/**
	 * remove all warnings from the opened file 
	 */
	private void clearWarnings() {
		File openedFile = editorServices.getOpenedFile();
		editorServices.saveFile(openedFile);
		resetRulesViolationCounter();
	}
}
