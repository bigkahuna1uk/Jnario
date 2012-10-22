package diverse;

import diverse.StackSpec;
import java.util.Stack;
import org.hamcrest.StringDescription;
import org.jnario.lib.Should;
import org.jnario.runner.ExampleGroupRunner;
import org.jnario.runner.Named;
import org.jnario.runner.Order;
import org.jnario.runner.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("all")
@RunWith(ExampleGroupRunner.class)
@Named("not empty")
public class StackNotEmptySpec extends StackSpec {
  @Subject
  public Stack subject;
  
  @Test
  @Named("increases size when pushing")
  @Order(2)
  public void _increasesSizeWhenPushing() throws Exception {
    this.subject.push("something");
    int _size = this.subject.size();
    boolean _doubleArrow = Should.operator_doubleArrow(Integer.valueOf(_size), Integer.valueOf(1));
    Assert.assertTrue("\nExpected subject.size => 1 but"
     + "\n     subject.size is " + new StringDescription().appendValue(Integer.valueOf(_size)).toString()
     + "\n     subject is " + new StringDescription().appendValue(this.subject).toString() + "\n", _doubleArrow);
    
  }
  
  @Test
  @Named("decreases size when popping")
  @Order(3)
  public void _decreasesSizeWhenPopping() throws Exception {
    this.subject.push("something");
    this.subject.pop();
    int _size = this.subject.size();
    boolean _doubleArrow = Should.operator_doubleArrow(Integer.valueOf(_size), Integer.valueOf(0));
    Assert.assertTrue("\nExpected subject.size => 0 but"
     + "\n     subject.size is " + new StringDescription().appendValue(Integer.valueOf(_size)).toString()
     + "\n     subject is " + new StringDescription().appendValue(this.subject).toString() + "\n", _doubleArrow);
    
  }
}