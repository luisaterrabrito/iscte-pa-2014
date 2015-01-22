package pa.iscde.stylechecker.internal;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ImportDeclaration;
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
	
	
	private static List<AbstractImportDeclarationRule> importStatementRules;
	private static List<AbstractTryStatementRule> tryStatementRules;
	private static List<AbstractVariableDeclarationRule> variableStatementRules;
	private static ProjectStyleChecker checker;
	private static JavaEditorServices editorServices;
	private Button btnRefresh;
	private Button btnClear;
	private ProjectBrowserServices browser;
	private Table tbRules;


	public StyleCheckerView() {
	
	}
	
	private void  initButtons(Composite viewArea) {
		
	Group btnsGroup_tools = new Group(viewArea, SWT.NONE);
	btnsGroup_tools.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
	GridData gd_btnsGroup_tools = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
	gd_btnsGroup_tools.heightHint = 18;
	btnsGroup_tools.setLayoutData(gd_btnsGroup_tools);
	
	btnRefresh = new Button(btnsGroup_tools, SWT.NONE);
	btnRefresh.setBounds(10, 0, 95, 28);
	btnRefresh.setText("Refresh");
	
	btnClear = new Button(btnsGroup_tools, SWT.NONE);
	
	viewArea.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 16, SWT.NORMAL));
	viewArea.setLayout(new GridLayout(1, false));

	tbRules = new Table(viewArea, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
	tbRules.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
	tbRules.setDragDetect(false);
	tbRules.setSelection(0);
	tbRules.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	tbRules.setHeaderVisible(true);
	tbRules.setLinesVisible(true);
	
	
	TableColumn tblclmnType = new TableColumn(tbRules, SWT.NONE);
	tblclmnType.setWidth(100);
	tblclmnType.setText("Type");
	
	TableColumn tblclmnName = new TableColumn(tbRules, SWT.NONE);
	tblclmnName.setWidth(100);
	tblclmnName.setText("Name");
	
	TableColumn tblclmnViolactions = new TableColumn(tbRules, SWT.LEFT);
	tblclmnViolactions.setMoveable(true);
	tblclmnViolactions.setWidth(100);
	tblclmnViolactions.setText("# Violations");
	
	btnClear.setBounds(111, 0, 95, 28);
	btnClear.setText("Clear");
	
	btnRefresh.addSelectionListener(new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			resetRulesViolationCounter();
			checkWorkspace(editorServices.getOpenedFile());
			packAll();
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {	
		}
	});
	
	tbRules.addListener(SWT.Selection, new Listener() {
		
		@Override
		public void handleEvent(Event event) {
			if(event.detail==SWT.CHECK) {
					TableItem[] items = tbRules.getSelection();
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
			
			//boot check
			checker.checkRootPackage(browser.getRootPackage());
			
			editorServices = JavaEditorActivator.getInstance().getServices();
			editorServices.addListener( new JavaEditorListener() {
				
				@Override
				public void fileSaved(File file) {
					resetRulesViolationCounter();
					checkWorkspace(file);				
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
			tbRules.selectAll();
			
	}
	
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
	private void checkWorkspace(File file) {
		
		StyleCheckerASTVisitor visitor = checker.getVisitor();
		visitor.reset();
		checker.checkRootPackage(browser.getRootPackage());
		List<ImportDeclaration> importDeclarations = visitor.getImportDeclarations();
		for (ImportDeclaration importDeclaration : importDeclarations) {
			for (AbstractImportDeclarationRule importRule : importStatementRules) {
				if(importRule.getActive() && importRule.check(importDeclaration)) {
					editorServices.addAnnotation(file, AnnotationType.WARNING,importRule.getWarningMessage(), importDeclaration.getStartPosition(), importDeclaration.getLength());
					importRule.setViolations(importRule.getViolations()+1);
				}
			}
		}
		
	}
	
	
	
	private void resetRulesViolationCounter() {
		for (AbstractImportDeclarationRule rule : importStatementRules) {
			rule.setViolations(0);
		}
		for (AbstractVariableDeclarationRule rule : variableStatementRules) {
			rule.setViolations(0);
		}
		for (AbstractTryStatementRule rule : tryStatementRules) {
			rule.setViolations(0);
		}
	}
	

	public void addRule(AbstractStyleRule rule) {
		TableItem item = new TableItem(tbRules, SWT.NONE);
		item.setText(Constant.TB_ITEN_RULE_NAME_IDX, rule.getClass().getSimpleName());
		item.setText(Constant.TB_ITEN_RULE_DESC_IDX, rule.getDescription());
		item.setText(Constant.TB_ITEN_RULE_VIOLATIONS_IDX, ""+rule.getViolations());
		item.setData(rule);
		packAll();
		tbRules.computeSize(SWT.FILL, SWT.FILL);
	}
	
	private void packAll() {
		TableItem[] items = tbRules.getItems();
		for (int i = 0; i < items.length; i++) {
			boolean active = ((AbstractStyleRule)items[i].getData()).getActive();
			if(active) {
				tbRules.select(i);
			}
		}
		for (int i = 0; i < Constant.TABLE_VIEW_NUM_COLUMNS; i++) {
	        tbRules.getColumn(i).pack();
	      }
		tbRules.update();
	}
}
