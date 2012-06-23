package org.jnario.suite.jvmmodel;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmAnnotationReference;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.util.TypeReferences;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor.IPostIndexingInitializing;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.jnario.Specification;
import org.jnario.jvmmodel.ExtendedJvmTypesBuilder;
import org.jnario.jvmmodel.JnarioJvmModelInferrer;
import org.jnario.jvmmodel.JunitAnnotationProvider;
import org.jnario.runner.Contains;
import org.jnario.runner.Named;
import org.jnario.suite.jvmmodel.SpecResolver;
import org.jnario.suite.jvmmodel.SuiteClassNameProvider;
import org.jnario.suite.jvmmodel.SuiteNode;
import org.jnario.suite.jvmmodel.SuiteNodeBuilder;
import org.jnario.suite.suite.Suite;
import org.jnario.suite.suite.SuiteFile;

@SuppressWarnings("all")
public class SuiteJvmModelInferrer extends JnarioJvmModelInferrer {
  @Inject
  private ExtendedJvmTypesBuilder _extendedJvmTypesBuilder;
  
  @Inject
  private SuiteClassNameProvider _suiteClassNameProvider;
  
  @Inject
  private JunitAnnotationProvider annotationProvider;
  
  @Inject
  private SpecResolver _specResolver;
  
  @Inject
  private TypeReferences types;
  
  @Inject
  private SuiteNodeBuilder _suiteNodeBuilder;
  
  public void infer(final EObject e, final IJvmDeclaredTypeAcceptor acceptor, final boolean preIndexingPhase) {
    boolean _checkClassPath = this.checkClassPath(e, this.annotationProvider);
    boolean _not = (!_checkClassPath);
    if (_not) {
      return;
    }
    boolean _not_1 = (!(e instanceof SuiteFile));
    if (_not_1) {
      return;
    }
    final SuiteFile suiteFile = ((SuiteFile) e);
    final Iterable<SuiteNode> nodes = this._suiteNodeBuilder.buildNodeModel(suiteFile);
    final Procedure1<SuiteNode> _function = new Procedure1<SuiteNode>() {
        public void apply(final SuiteNode it) {
          SuiteJvmModelInferrer.this.infer(it, acceptor);
        }
      };
    IterableExtensions.<SuiteNode>forEach(nodes, _function);
  }
  
  public JvmGenericType infer(final SuiteNode node, final IJvmDeclaredTypeAcceptor acceptor) {
    JvmGenericType _xblockexpression = null;
    {
      final Suite suite = node.getSuite();
      String _qualifiedClassName = this._suiteClassNameProvider.getQualifiedClassName(suite);
      final JvmGenericType suiteClass = this._extendedJvmTypesBuilder.toClass(suite, _qualifiedClassName);
      List<SuiteNode> _children = node.getChildren();
      final Function1<SuiteNode,JvmGenericType> _function = new Function1<SuiteNode,JvmGenericType>() {
          public JvmGenericType apply(final SuiteNode it) {
            JvmGenericType _infer = SuiteJvmModelInferrer.this.infer(it, acceptor);
            return _infer;
          }
        };
      List<JvmGenericType> _map = ListExtensions.<SuiteNode, JvmGenericType>map(_children, _function);
      final Set<JvmGenericType> subSuites = IterableExtensions.<JvmGenericType>toSet(_map);
      IPostIndexingInitializing<JvmGenericType> _accept = acceptor.<JvmGenericType>accept(suiteClass);
      final Procedure1<JvmGenericType> _function_1 = new Procedure1<JvmGenericType>() {
          public void apply(final JvmGenericType it) {
            EList<JvmAnnotationReference> _annotations = it.getAnnotations();
            JvmAnnotationReference _exampleGroupRunnerAnnotation = SuiteJvmModelInferrer.this.annotationProvider.getExampleGroupRunnerAnnotation(suite);
            SuiteJvmModelInferrer.this._extendedJvmTypesBuilder.<JvmAnnotationReference>operator_add(_annotations, _exampleGroupRunnerAnnotation);
            EList<JvmAnnotationReference> _annotations_1 = it.getAnnotations();
            String _describe = SuiteJvmModelInferrer.this._suiteClassNameProvider.describe(suite);
            JvmAnnotationReference _annotation = SuiteJvmModelInferrer.this._extendedJvmTypesBuilder.toAnnotation(suite, Named.class, _describe);
            SuiteJvmModelInferrer.this._extendedJvmTypesBuilder.<JvmAnnotationReference>operator_add(_annotations_1, _annotation);
            Iterable<JvmType> _children = SuiteJvmModelInferrer.this.children(suite);
            final Iterable<JvmType> children = Iterables.<JvmType>concat(_children, subSuites);
            boolean _isEmpty = IterableExtensions.isEmpty(children);
            boolean _not = (!_isEmpty);
            if (_not) {
              EList<JvmAnnotationReference> _annotations_2 = it.getAnnotations();
              Set<JvmType> _set = IterableExtensions.<JvmType>toSet(children);
              JvmAnnotationReference _annotation_1 = SuiteJvmModelInferrer.this._extendedJvmTypesBuilder.toAnnotation(suite, Contains.class, _set);
              SuiteJvmModelInferrer.this._extendedJvmTypesBuilder.<JvmAnnotationReference>operator_add(_annotations_2, _annotation_1);
            }
            SuiteJvmModelInferrer.this.initialize(suite, it);
          }
        };
      _accept.initializeLater(_function_1);
      _xblockexpression = (suiteClass);
    }
    return _xblockexpression;
  }
  
  public Iterable<JvmType> children(final Suite suite) {
    List<JvmType> _xblockexpression = null;
    {
      final List<Specification> specs = this._specResolver.resolveSpecs(suite);
      final Function1<Specification,String> _function = new Function1<Specification,String>() {
          public String apply(final Specification it) {
            String _qualifiedClassName = SuiteJvmModelInferrer.this._suiteClassNameProvider.getQualifiedClassName(it);
            return _qualifiedClassName;
          }
        };
      final List<String> types = ListExtensions.<Specification, String>map(specs, _function);
      final Function1<String,JvmType> _function_1 = new Function1<String,JvmType>() {
          public JvmType apply(final String it) {
            JvmTypeReference _typeForName = SuiteJvmModelInferrer.this.types.getTypeForName(it, suite);
            JvmType _type = _typeForName==null?(JvmType)null:_typeForName.getType();
            return _type;
          }
        };
      List<JvmType> _map = ListExtensions.<String, JvmType>map(types, _function_1);
      _xblockexpression = (_map);
    }
    return _xblockexpression;
  }
}
