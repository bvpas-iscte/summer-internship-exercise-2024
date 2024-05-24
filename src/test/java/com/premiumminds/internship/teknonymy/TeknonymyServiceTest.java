package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy", 'F', null, LocalDateTime.of(1080, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(expected, result);
  }

  @Test
  public void PersonTwoChildrenDifAgesTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ 
          new Person("Holy", 'F', null, LocalDateTime.of(1080, 1, 1, 0, 0)),  
          new Person("Claire", 'F', null, LocalDateTime.of(1070, 1, 1, 0, 0)) 
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Claire";
    assertEquals(expected, result);
  }

  @Test
  public void PersonMultipleGenerationsOfDescendantsTest() {
    Person grandchild = new Person("James", 'M', null, LocalDateTime.of(1070, 1, 1, 0, 0));
    Person child = new Person("Holy", 'F', new Person[]{grandchild}, LocalDateTime.of(1046, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{child}, LocalDateTime.of(1044, 1, 1, 0, 0));
    
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of James";
    assertEquals(expected, result);
  }

  @Test
  public void PersonWithGreatGrandchildTest() {
    Person greatGrandchild = new Person("Lily", 'F', null, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person grandchild = new Person("James", 'M', new Person[]{greatGrandchild}, LocalDateTime.of(1070, 1, 1, 0, 0));
    Person child = new Person("Holy", 'F', new Person[]{grandchild}, LocalDateTime.of(1046, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{child}, LocalDateTime.of(1044, 1, 1, 0, 0));
    
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandfather of Lily";
    assertEquals(expected, result);
  }

  @Test
  public void FemalePersonWithDescendantsTest() {
    Person child = new Person("Anna", 'F', null, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person person = new Person("Mary", 'F', new Person[]{child}, LocalDateTime.of(1040, 1, 1, 0, 0));
    
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "mother of Anna";
    assertEquals(expected, result);
  }

  @Test
  public void PersonWithMultipleGenerationsAndOldestDeepestDescendantTest() {
    Person child1 = new Person("Anna", 'F', null, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person grandchild1 = new Person("Emma", 'F', null, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child2 = new Person("Ben", 'M', new Person[]{grandchild1}, LocalDateTime.of(1055, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{child1, child2}, LocalDateTime.of(1030, 1, 1, 0, 0));
    
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of Emma";
    assertEquals(expected, result);
  }

  @Test
public void GreatGreatGreatGreatGrandmotherTest() {
    Person greatgreatgreatgreatGrandchild = new Person("Joana", 'F', null, LocalDateTime.of(1140, 1, 1, 0, 0));
    Person greatgreatgreatgrandchild = new Person("Mary", 'F', new Person[]{greatgreatgreatgreatGrandchild }, LocalDateTime.of(1120, 1, 1, 0, 0));
    Person greatgreatgrandchild= new Person("Anna", 'F', new Person[]{greatgreatgreatgrandchild }, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person greatgrandchild= new Person("Lilly", 'F', new Person[]{greatgreatgrandchild}, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person grandchild= new Person("John", 'M', new Person[]{greatgrandchild}, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child= new Person("Bea", 'F', new Person[]{grandchild}, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person mother= new Person("Rosa", 'F', new Person[]{child}, LocalDateTime.of(1080, 1, 1, 0, 0));
    
    String result = new TeknonymyService().getTeknonymy(mother);
    String expected = "great-great-great-great-grandmother of Joana";
    assertEquals(expected, result);
}

}