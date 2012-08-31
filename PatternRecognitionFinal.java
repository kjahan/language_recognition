/* Now you will be able to perform actions when a button is clicked
to get and place text in/out a textfield and to get the state of checkboxes.
This example will only let the button do actions.
*/

import java.util.*;
import java.awt.*;
import java.applet.*;
import java.io.*;
import java.awt.event.*;	// import an extra class for the ActionListener

// Tells the applet you will be using the ActionListener methods.

public class PatternRecognitionFinal extends Applet implements ActionListener
{

     Button Check;
     TextField nameField;

	 // Hashtable<String> numbers = new Hashtable<String>();
	 // In your case, that would translate into having the following code:
	 // Hashtable<String,Object> myHashtable = new Hashtable<String,Object>;
     // If your hashtable were to contain String keys and YourClass objects, you could define your hashtable as follows:
     // Hashtable<String,YourClass> myHashtable = new Hashtable<String,YourClass>();
	 Hashtable<String,Double> ENProbMap = new Hashtable<String,Double>();
	 Hashtable<String,Double> FNProbMap = new Hashtable<String,Double>();
	 Hashtable<String,Double> SPProbMap = new Hashtable<String,Double>();

     StringTokenizer st;

  	 double ENLikelihoodFN = 0.0;
  	 double FNLikelihoodFN = 0.0;
  	 double SPLikelihoodFN = 0.0;

	 static final String ZEROES = "000000000000";
  	 static final String BLANKS = "            ";

	 String prediction;

     public void init()
     {
		  // Now we will use the FlowLayout
          setLayout(new FlowLayout());
          Check = new Button("CHECK YOUR SCENTENCE!");

          nameField = new TextField("Type here your sentence!",50);
          add(Check);
          add(nameField);

		  // Attach actions to the components
          Check.addActionListener(this);
		  setupHash();
      }

	 // Here we will show the results of our actions
	 public void paint(Graphics g)
	 {
		  // Now that the color is set you can get the text out the TextField
		  // like this
		  Double EN = ENLikelihoodFN;
		  Double FN = FNLikelihoodFN;
		  Double SP = SPLikelihoodFN;
		  g.drawString(nameField.getText(),20,100);
		  g.drawString("-------------------------LIKELIHOODS--------------------------",20,110);
		  g.drawString("The Likelihood to be ENGLISH: " + EN.toString(),20,120);
		  g.drawString("The Likelihood to be FRENCH: " + FN.toString(),20,130);
		  g.drawString("The Likelihood to be SPANISH: " + SP.toString(),20,140);
		  g.drawString("--------------------------PREDICTION-------------------------",20,150);
		  g.drawString("YOUR SCENTENCE IS PROBABLY: **************" + prediction + "****************", 20, 160);
     }

	 // When the button is clicked this method will get automatically called
	 // This is where you specify all actions.

     public void actionPerformed(ActionEvent evt)
     {
		  // Here we will ask what component called this method
	  	  if (evt.getSource() == Check)
	  	  {
			  double result = 0.0;
			  SentenceFilter();
			  //Do prediction!
			  result = ENLikelihoodFN;
			  prediction = "ENGLISH";

			  if(result < FNLikelihoodFN){
				  prediction = "FRENCH";
				  result = FNLikelihoodFN;
			  }

			  if (result < SPLikelihoodFN)
			  	prediction = "SPANISH";

			  repaint();
	      }

     }// Process key

	 public void setupHash(){
		 ENProbMap.put("cases,",0.022727272727272728);
		 ENProbMap.put("Puerto",0.022727272727272728);
		 ENProbMap.put("explicit",0.03409090909090909);
		 ENProbMap.put("public",0.022727272727272728);
		 ENProbMap.put("April",0.022727272727272728);
		 ENProbMap.put("new",0.045454545454545456);
		 ENProbMap.put("And",0.022727272727272728);
		 ENProbMap.put("all",0.045454545454545456);
		 ENProbMap.put("provides",0.022727272727272728);
		 ENProbMap.put("few",0.022727272727272728);
		 ENProbMap.put("McCain",0.03409090909090909);
		 ENProbMap.put("efforts",0.022727272727272728);
		 ENProbMap.put("schools.",0.022727272727272728);
		 ENProbMap.put("Alito",0.045454545454545456);
		 ENProbMap.put("temporary",0.022727272727272728);
		 ENProbMap.put("director",0.022727272727272728);
		 ENProbMap.put("earlier",0.022727272727272728);
		 ENProbMap.put("carried",0.022727272727272728);
		 ENProbMap.put("too",0.03409090909090909);
		 ENProbMap.put("Court",0.022727272727272728);
		 ENProbMap.put("out",0.045454545454545456);
		 ENProbMap.put("our",0.022727272727272728);
		 ENProbMap.put("Russia",0.03409090909090909);
		 ENProbMap.put("saddled",0.022727272727272728);
		 ENProbMap.put("gain",0.022727272727272728);
		 ENProbMap.put("Civil",0.022727272727272728);
		 ENProbMap.put("data",0.022727272727272728);
		 ENProbMap.put("lull.",0.022727272727272728);
		 ENProbMap.put("join",0.022727272727272728);
		 ENProbMap.put("levels",0.022727272727272728);
		 ENProbMap.put("Supreme",0.022727272727272728);
		 ENProbMap.put("Dr.",0.022727272727272728);
		 ENProbMap.put("clear",0.03409090909090909);
		 ENProbMap.put("ages.",0.022727272727272728);
		 ENProbMap.put("same",0.03409090909090909);
		 ENProbMap.put("interpreted",0.03409090909090909);
		 ENProbMap.put("lowest",0.03409090909090909);
		 ENProbMap.put("refused",0.022727272727272728);
		 ENProbMap.put("just",0.022727272727272728);
		 ENProbMap.put("sale",0.022727272727272728);
		 ENProbMap.put("age",0.06818181818181818);
		 ENProbMap.put("reduce",0.022727272727272728);
		 ENProbMap.put("yet",0.03409090909090909);
		 ENProbMap.put("whether",0.022727272727272728);
		 ENProbMap.put("children",0.06818181818181818);
		 ENProbMap.put("context",0.022727272727272728);
		 ENProbMap.put("cover",0.022727272727272728);
		 ENProbMap.put("officials",0.022727272727272728);
		 ENProbMap.put("chief",0.022727272727272728);
		 ENProbMap.put("Hospital",0.022727272727272728);
		 ENProbMap.put("while",0.022727272727272728);
		 ENProbMap.put("other",0.03409090909090909);
		 ENProbMap.put("Ludwig,",0.022727272727272728);
		 ENProbMap.put("know",0.022727272727272728);
		 ENProbMap.put("weight",0.022727272727272728);
		 ENProbMap.put("representing",0.022727272727272728);
		 ENProbMap.put("countries",0.03409090909090909);
		 ENProbMap.put("said",0.125);
		 ENProbMap.put("against",0.03409090909090909);
		 ENProbMap.put("herself",0.022727272727272728);
		 ENProbMap.put("issued",0.022727272727272728);
		 ENProbMap.put("increase",0.045454545454545456);
		 ENProbMap.put("but",0.03409090909090909);
		 ENProbMap.put("did",0.022727272727272728);
		 ENProbMap.put("the",0.7159090909090909);	//interesting
		 ENProbMap.put("holds",0.022727272727272728);
		 ENProbMap.put("one",0.022727272727272728);
		 ENProbMap.put("opinion,",0.022727272727272728);
		 ENProbMap.put("junk",0.022727272727272728);
		 ENProbMap.put("Act",0.022727272727272728);
		 ENProbMap.put("much",0.045454545454545456);
		 ENProbMap.put("complaints",0.022727272727272728);
		 ENProbMap.put("note",0.022727272727272728);
		 ENProbMap.put("But",0.03409090909090909);
		 ENProbMap.put("The",0.23863636363636365);	//interesting
		 ENProbMap.put("Justice",0.056818181818181816);
		 ENProbMap.put("when",0.045454545454545456);
		 ENProbMap.put("weapons,",0.022727272727272728);
		 ENProbMap.put("appeals",0.022727272727272728);
		 ENProbMap.put("One",0.022727272727272728);
		 ENProbMap.put("very",0.022727272727272728);
		 ENProbMap.put("American",0.045454545454545456);
		 ENProbMap.put("prohibiting",0.022727272727272728);
		 ENProbMap.put("Commission",0.022727272727272728);
		 ENProbMap.put("soon",0.022727272727272728);
		 ENProbMap.put("protection",0.022727272727272728);
		 ENProbMap.put("times",0.022727272727272728);
		 ENProbMap.put("anti-obesity",0.022727272727272728);
		 ENProbMap.put("program",0.022727272727272728);
		 ENProbMap.put("with",0.18181818181818182);	//interesting
		 ENProbMap.put("what",0.022727272727272728);
		 ENProbMap.put("because",0.056818181818181816);
		 ENProbMap.put("who",0.045454545454545456);
		 ENProbMap.put("language",0.03409090909090909);
		 ENProbMap.put("husband",0.022727272727272728);
		 ENProbMap.put("which",0.10227272727272728);
		 ENProbMap.put("statute",0.022727272727272728);
		 ENProbMap.put("began",0.022727272727272728);
		 ENProbMap.put("noted",0.022727272727272728);
		 ENProbMap.put("law",0.03409090909090909);
		 ENProbMap.put("Title",0.022727272727272728);
		 ENProbMap.put("permanent",0.022727272727272728);
		 ENProbMap.put("United",0.056818181818181816);
		 ENProbMap.put("before",0.022727272727272728);
		 ENProbMap.put("growing",0.022727272727272728);
		 ENProbMap.put("limit",0.022727272727272728);
		 ENProbMap.put("school",0.022727272727272728);
		 ENProbMap.put("since",0.03409090909090909);
		 ENProbMap.put("this",0.07954545454545454);
		 ENProbMap.put("year",0.045454545454545456);
		 ENProbMap.put("based",0.022727272727272728);
		 ENProbMap.put("extraordinarily",0.022727272727272728);
		 ENProbMap.put("country.",0.022727272727272728);
		 ENProbMap.put("have",0.07954545454545454);
		 ENProbMap.put("federal",0.09090909090909091);
		 ENProbMap.put("decision",0.022727272727272728);
		 ENProbMap.put("economy",0.03409090909090909);
		 ENProbMap.put("Internet",0.022727272727272728);
		 ENProbMap.put("1960s",0.022727272727272728);
		 ENProbMap.put("news",0.022727272727272728);
		 ENProbMap.put("provision",0.056818181818181816);
		 ENProbMap.put("also",0.03409090909090909);
		 ENProbMap.put("Ms.",0.022727272727272728);
		 ENProbMap.put("season",0.022727272727272728);
		 ENProbMap.put("Bush",0.022727272727272728);
		 ENProbMap.put("glimmer",0.022727272727272728);
		 ENProbMap.put("It",0.022727272727272728);
		 ENProbMap.put("reports",0.022727272727272728);
		 ENProbMap.put("In",0.09090909090909091);
		 ENProbMap.put("lull",0.022727272727272728);
		 ENProbMap.put("up,",0.022727272727272728);
		 ENProbMap.put("Mr.",0.03409090909090909);
		 ENProbMap.put("Tuesday",0.03409090909090909);
		 ENProbMap.put("they",0.056818181818181816);
		 ENProbMap.put("was",0.09090909090909091);
		 ENProbMap.put("four",0.03409090909090909);
		 ENProbMap.put("there",0.045454545454545456);
		 ENProbMap.put("study",0.022727272727272728);
		 ENProbMap.put("trend",0.022727272727272728);
		 ENProbMap.put("Service",0.022727272727272728);
		 ENProbMap.put("will",0.045454545454545456);
		 ENProbMap.put("workplace",0.022727272727272728);
		 ENProbMap.put("herself.",0.022727272727272728);
		 ENProbMap.put("showed",0.03409090909090909);
		 ENProbMap.put("court",0.045454545454545456);
		 ENProbMap.put("period",0.022727272727272728);
		 ENProbMap.put("many",0.045454545454545456);
		 ENProbMap.put("applies",0.03409090909090909);
		 ENProbMap.put("we",0.022727272727272728);
		 ENProbMap.put("worse",0.022727272727272728);
		 ENProbMap.put("itÆs",0.022727272727272728);
		 ENProbMap.put("El",0.03409090909090909);
		 ENProbMap.put("Boston.",0.022727272727272728);
		 ENProbMap.put("Boston,",0.022727272727272728);
		 ENProbMap.put("obesity,",0.022727272727272728);
		 ENProbMap.put("compared",0.022727272727272728);
		 ENProbMap.put("March",0.022727272727272728);
		 ENProbMap.put("discrimination.",0.03409090909090909);
		 ENProbMap.put("where",0.022727272727272728);
		 ENProbMap.put("that",0.3522727272727273);	//interesting
		 ENProbMap.put("than",0.056818181818181816);
		 ENProbMap.put("discrimination",0.06818181818181818);
		 ENProbMap.put("Malika",0.03409090909090909);
		 ENProbMap.put("up",0.03409090909090909);
		 ENProbMap.put("homes",0.056818181818181816);
		 ENProbMap.put("to",0.4772727272727273);	//interesting
		 ENProbMap.put("ItÆs",0.045454545454545456);
		 ENProbMap.put("States",0.045454545454545456);
		 ENProbMap.put("weight-related",0.022727272727272728);
		 ENProbMap.put("childhood",0.045454545454545456);
		 ENProbMap.put("three",0.022727272727272728);
		 ENProbMap.put("Retaliation",0.022727272727272728);
		 ENProbMap.put("food",0.022727272727272728);
		 ENProbMap.put("so",0.022727272727272728);
		 ENProbMap.put("only",0.022727272727272728);
		 ENProbMap.put("does",0.022727272727272728);
		 ENProbMap.put("As",0.022727272727272728);
		 ENProbMap.put("should",0.022727272727272728);
		 ENProbMap.put("plateau",0.022727272727272728);
		 ENProbMap.put("bad",0.022727272727272728);
		 ENProbMap.put("obese,",0.03409090909090909);
		 ENProbMap.put("hurting",0.022727272727272728);
		 ENProbMap.put("lawsuit",0.022727272727272728);
		 ENProbMap.put("home-buying",0.022727272727272728);
		 ENProbMap.put("across",0.022727272727272728);
		 ENProbMap.put("their",0.045454545454545456);
		 ENProbMap.put("men",0.022727272727272728);
		 ENProbMap.put("most",0.03409090909090909);
		 ENProbMap.put("percent,",0.022727272727272728);
		 ENProbMap.put("workers",0.022727272727272728);
		 ENProbMap.put("or",0.11363636363636363);	//interesting
		 ENProbMap.put("on",0.18181818181818182);	//interesting
		 ENProbMap.put("more",0.056818181818181816);
		 ENProbMap.put("administration",0.022727272727272728);
		 ENProbMap.put("activity",0.022727272727272728);
		 ENProbMap.put("of",0.5);	//interesting
		 ENProbMap.put("his",0.03409090909090909);
		 ENProbMap.put("largely",0.022727272727272728);
		 ENProbMap.put("David",0.022727272727272728);
		 ENProbMap.put("months",0.022727272727272728);
		 ENProbMap.put("no",0.03409090909090909);
		 ENProbMap.put("understood",0.022727272727272728);
		 ENProbMap.put("tumbled",0.022727272727272728);
		 ENProbMap.put("economists",0.022727272727272728);
		 ENProbMap.put("from",0.14772727272727273);
		 ENProbMap.put("my",0.022727272727272728);
		 ENProbMap.put("made",0.03409090909090909);
		 ENProbMap.put("cases",0.022727272727272728);
		 ENProbMap.put("take",0.022727272727272728);
		 ENProbMap.put("hope,",0.022727272727272728);
		 ENProbMap.put("over",0.022727272727272728);
		 ENProbMap.put("major",0.022727272727272728);
		 ENProbMap.put("although",0.022727272727272728);
		 ENProbMap.put("speech",0.022727272727272728);
		 ENProbMap.put("percent",0.13636363636363635);
		 ENProbMap.put("teenagers",0.022727272727272728);
		 ENProbMap.put("market",0.03409090909090909);
		 ENProbMap.put("markets",0.022727272727272728);
		 ENProbMap.put("overweight",0.022727272727272728);
		 ENProbMap.put("jihad.",0.022727272727272728);
		 ENProbMap.put("health",0.03409090909090909);
		 ENProbMap.put("role",0.045454545454545456);
		 ENProbMap.put("her",0.056818181818181816);
		 ENProbMap.put("doctors",0.022727272727272728);
		 ENProbMap.put("employees",0.03409090909090909);
		 ENProbMap.put("prices",0.056818181818181816);
		 ENProbMap.put("case,",0.03409090909090909);
		 ENProbMap.put("such",0.03409090909090909);
		 ENProbMap.put("said.",0.022727272727272728);
		 ENProbMap.put("different",0.022727272727272728);
		 ENProbMap.put("arms",0.022727272727272728);
		 ENProbMap.put("about",0.056818181818181816);
		 ENProbMap.put("would",0.022727272727272728);
		 ENProbMap.put("once",0.022727272727272728);
		 ENProbMap.put("it",0.09090909090909091);
		 ENProbMap.put("six",0.022727272727272728);
		 ENProbMap.put("is",0.26136363636363635);	//interesting
		 ENProbMap.put("in",0.5);	//interesting
		 ENProbMap.put("Her",0.022727272727272728);
		 ENProbMap.put("if",0.045454545454545456);
		 ENProbMap.put("not",0.1590909090909091);	//interesting
		 ENProbMap.put("result",0.03409090909090909);
		 ENProbMap.put("even",0.07954545454545454);
		 ENProbMap.put("for",0.125);	//interesting
		 ENProbMap.put("retaliation",0.045454545454545456);
		 ENProbMap.put("Iraq",0.022727272727272728);
		 ENProbMap.put("generation",0.022727272727272728);
		 ENProbMap.put("home",0.03409090909090909);
		 ENProbMap.put("last",0.056818181818181816);
		 ENProbMap.put("still",0.03409090909090909);
		 ENProbMap.put("Aroud",0.03409090909090909);
		 ENProbMap.put("she",0.07954545454545454);
		 ENProbMap.put("sales",0.056818181818181816);
		 ENProbMap.put("its",0.03409090909090909);
		 ENProbMap.put("has",0.09090909090909091);
		 ENProbMap.put("problems",0.03409090909090909);
		 ENProbMap.put("had",0.056818181818181816);
		 ENProbMap.put("were",0.09090909090909091);
		 ENProbMap.put("She",0.045454545454545456);
		 ENProbMap.put("physical",0.022727272727272728);
		 ENProbMap.put("Congress",0.045454545454545456);
		 ENProbMap.put("first",0.03409090909090909);
		 ENProbMap.put("homes,",0.022727272727272728);
		 ENProbMap.put("true",0.022727272727272728);
		 ENProbMap.put("ChildrenÆs",0.022727272727272728);
		 ENProbMap.put("entire",0.022727272727272728);
		 ENProbMap.put("into",0.022727272727272728);
		 ENProbMap.put("prevalence",0.022727272727272728);
		 ENProbMap.put("two",0.06818181818181818);
		 ENProbMap.put("are",0.1590909090909091);	//interesting
		 ENProbMap.put("do",0.03409090909090909);
		 ENProbMap.put("simply",0.022727272727272728);
		 ENProbMap.put("After",0.022727272727272728);
		 ENProbMap.put("homeless",0.022727272727272728);
		 ENProbMap.put("Two",0.022727272727272728);
		 ENProbMap.put("areas",0.022727272727272728);
		 ENProbMap.put("a",0.4659090909090909);	//interesting
		 ENProbMap.put("32",0.022727272727272728);
		 ENProbMap.put("Strategic",0.022727272727272728);
		 ENProbMap.put("women",0.03409090909090909);
		 ENProbMap.put("by",0.11363636363636363);	//interesting
		 ENProbMap.put("Employment",0.022727272727272728);
		 ENProbMap.put("falling,",0.022727272727272728);
		 ENProbMap.put("I",0.022727272727272728);
		 ENProbMap.put("retaliation.",0.03409090909090909);
		 ENProbMap.put("be",0.09090909090909091);
		 ENProbMap.put("25",0.022727272727272728);
		 ENProbMap.put("20",0.022727272727272728);
		 ENProbMap.put("say",0.022727272727272728);
		 ENProbMap.put("at",0.11363636363636363);	//interesting
		 ENProbMap.put("as",0.1590909090909091);	//interesting
		 ENProbMap.put("an",0.09090909090909091);
		 ENProbMap.put("nuclear",0.056818181818181816);
		 ENProbMap.put("11",0.03409090909090909);
		 ENProbMap.put("years",0.03409090909090909);
		 ENProbMap.put("and",0.3409090909090909);	//interesting
		 ENProbMap.put("well",0.022727272727272728);
		 ENProbMap.put("obesity",0.022727272727272728);
		 ENProbMap.put("available",0.022727272727272728);
		 ENProbMap.put("like",0.03409090909090909);
		 ENProbMap.put("binding",0.022727272727272728);
		 ENProbMap.put("fight",0.022727272727272728);
		 //EN Tokens: 88
		 //English Hashtable contains 944 key value pair.
		 //----------------------------------------------------
		 FNProbMap.put("car",0.013245033112582781);
		 FNProbMap.put("filles",0.019867549668874173);
		 FNProbMap.put("juin",0.013245033112582781);
		 FNProbMap.put("ils",0.019867549668874173);
		 FNProbMap.put("debut",0.019867549668874173);
		 FNProbMap.put("travail",0.052980132450331126);
		 FNProbMap.put("chez",0.019867549668874173);
		 FNProbMap.put("nouvelle",0.019867549668874173);
		 FNProbMap.put("l'ONU",0.019867549668874173);
		 FNProbMap.put("chef",0.013245033112582781);
		 FNProbMap.put("qu'en",0.013245033112582781);
		 FNProbMap.put("proposition",0.013245033112582781);
		 FNProbMap.put("valeurs",0.013245033112582781);
		 FNProbMap.put("meteo",0.013245033112582781);
		 FNProbMap.put("d'affaires",0.019867549668874173);
		 FNProbMap.put("porte-parole",0.019867549668874173);
		 FNProbMap.put("dont",0.013245033112582781);
		 FNProbMap.put("l'audiovisuel",0.039735099337748346);
		 FNProbMap.put("titre",0.019867549668874173);
		 FNProbMap.put("affirme",0.013245033112582781);
		 FNProbMap.put("quoi",0.013245033112582781);
		 FNProbMap.put("loi",0.039735099337748346);
		 FNProbMap.put("avec",0.06622516556291391);
		 FNProbMap.put("temps",0.019867549668874173);
		 FNProbMap.put("groupe",0.013245033112582781);
		 FNProbMap.put("match.",0.013245033112582781);
		 FNProbMap.put("match,",0.013245033112582781);
		 FNProbMap.put("declare",0.026490066225165563);
		 FNProbMap.put("trouver",0.026490066225165563);
		 FNProbMap.put("Bernard",0.019867549668874173);
		 FNProbMap.put("Monde",0.013245033112582781);
		 FNProbMap.put("l'entreprise",0.013245033112582781);
		 FNProbMap.put("solution",0.013245033112582781);
		 FNProbMap.put("fait",0.0728476821192053);
		 FNProbMap.put("l'atelier",0.013245033112582781);
		 FNProbMap.put("depuis",0.046357615894039736);
		 FNProbMap.put("l'Otan",0.013245033112582781);
		 FNProbMap.put("l'Etat",0.013245033112582781);
		 FNProbMap.put("Constitution",0.013245033112582781);
		 FNProbMap.put("deux",0.07947019867549669);
		 FNProbMap.put("selon",0.033112582781456956);
		 FNProbMap.put("payer",0.013245033112582781);
		 FNProbMap.put("public",0.039735099337748346);
		 FNProbMap.put("direction",0.013245033112582781);
		 FNProbMap.put("chiffre",0.013245033112582781);
		 FNProbMap.put("ont",0.09271523178807947);
		 FNProbMap.put("cette",0.052980132450331126);
		 FNProbMap.put("general",0.013245033112582781);
		 FNProbMap.put("matin,",0.026490066225165563);
		 FNProbMap.put("grand",0.013245033112582781);
		 FNProbMap.put("Si",0.013245033112582781);
		 FNProbMap.put("securite",0.013245033112582781);
		 FNProbMap.put("centre",0.019867549668874173);
		 FNProbMap.put("article,",0.013245033112582781);
		 FNProbMap.put("reforme",0.013245033112582781);
		 FNProbMap.put("face",0.019867549668874173);
		 FNProbMap.put("KFOR,",0.013245033112582781);
		 FNProbMap.put("n'y",0.013245033112582781);
		 FNProbMap.put("les",0.33112582781456956);	//interesting
		 FNProbMap.put("Cope",0.019867549668874173);
		 FNProbMap.put("femmes,",0.013245033112582781);
		 FNProbMap.put("Les",0.052980132450331126);
		 FNProbMap.put("indique",0.039735099337748346);
		 FNProbMap.put("contrat",0.013245033112582781);
		 FNProbMap.put("mais",0.026490066225165563);
		 FNProbMap.put("explique",0.013245033112582781);
		 FNProbMap.put("repliques",0.013245033112582781);
		 FNProbMap.put("mai,",0.026490066225165563);
		 FNProbMap.put("ete",0.0728476821192053);
		 FNProbMap.put("est",0.0728476821192053);
		 FNProbMap.put("Le",0.046357615894039736);
		 FNProbMap.put("La",0.033112582781456956);
		 FNProbMap.put("devrait",0.019867549668874173);
		 FNProbMap.put("M.",0.13245033112582782);	//interesting
		 FNProbMap.put("gouvernement",0.013245033112582781);
		 FNProbMap.put("etait",0.033112582781456956);
		 FNProbMap.put("deja",0.013245033112582781);
		 FNProbMap.put("faire",0.052980132450331126);
		 FNProbMap.put("Je",0.039735099337748346);
		 FNProbMap.put("Il",0.019867549668874173);
		 FNProbMap.put("piste",0.019867549668874173);
		 FNProbMap.put("n'ont",0.013245033112582781);
		 FNProbMap.put("l'UE",0.013245033112582781);
		 FNProbMap.put("alors",0.019867549668874173);
		 FNProbMap.put("une",0.17218543046357615);	//interesting
		 FNProbMap.put("garantie",0.013245033112582781);
		 FNProbMap.put("ailleurs,",0.019867549668874173);
		 FNProbMap.put("Sarkozy",0.033112582781456956);
		 FNProbMap.put("solutions",0.013245033112582781);
		 FNProbMap.put("Une",0.013245033112582781);
		 FNProbMap.put("financement",0.033112582781456956);
		 FNProbMap.put("appel",0.013245033112582781);
		 FNProbMap.put("Cette",0.019867549668874173);
		 FNProbMap.put("Et",0.013245033112582781);
		 FNProbMap.put("En",0.019867549668874173);
		 FNProbMap.put("c'est",0.019867549668874173);
		 FNProbMap.put("membre",0.019867549668874173);
		 FNProbMap.put("De",0.013245033112582781);
		 FNProbMap.put("seisme",0.013245033112582781);
		 FNProbMap.put("poursuit",0.013245033112582781);
		 FNProbMap.put("augmentation",0.026490066225165563);
		 FNProbMap.put("dimanche",0.013245033112582781);
		 FNProbMap.put("n'est",0.019867549668874173);
		 FNProbMap.put("leurs",0.06622516556291391);
		 FNProbMap.put("Cela",0.013245033112582781);
		 FNProbMap.put("recherche",0.013245033112582781);
		 FNProbMap.put("volonte",0.013245033112582781);
		 FNProbMap.put("avoir",0.026490066225165563);
		 FNProbMap.put("donne",0.013245033112582781);
		 FNProbMap.put("Ban",0.033112582781456956);
		 FNProbMap.put("Bruxelles",0.013245033112582781);
		 FNProbMap.put("numero",0.013245033112582781);
		 FNProbMap.put("Bloche",0.013245033112582781);
		 FNProbMap.put("dollars",0.013245033112582781);
		 FNProbMap.put("vous",0.013245033112582781);
		 FNProbMap.put("comme",0.052980132450331126);
		 FNProbMap.put("autre",0.013245033112582781);
		 FNProbMap.put("moins",0.019867549668874173);
		 FNProbMap.put("minutes",0.013245033112582781);
		 FNProbMap.put("etaient",0.019867549668874173);
		 FNProbMap.put("Noah.",0.013245033112582781);
		 FNProbMap.put("Noah,",0.013245033112582781);
		 FNProbMap.put("trois",0.019867549668874173);
		 FNProbMap.put("entre",0.033112582781456956);
		 FNProbMap.put("lors",0.013245033112582781);
		 FNProbMap.put("generale",0.019867549668874173);
		 FNProbMap.put("\"Pour",0.013245033112582781);
		 FNProbMap.put("pistes",0.013245033112582781);
		 FNProbMap.put("C'est",0.013245033112582781);
		 FNProbMap.put("huit",0.019867549668874173);
		 FNProbMap.put("salaries",0.013245033112582781);
		 FNProbMap.put("80",0.013245033112582781);
		 FNProbMap.put("elles",0.026490066225165563);
		 FNProbMap.put("non",0.013245033112582781);
		 FNProbMap.put("chaines",0.019867549668874173);
		 FNProbMap.put("dans",0.19205298013245034);	//interesting
		 FNProbMap.put("premier",0.013245033112582781);
		 FNProbMap.put("qui",0.13245033112582782);	//interesting
		 FNProbMap.put("que",0.2185430463576159);	//interesting
		 FNProbMap.put("aux",0.06622516556291391);
		 FNProbMap.put("vont",0.013245033112582781);
		 FNProbMap.put("Republique",0.013245033112582781);
		 FNProbMap.put("Jerome",0.013245033112582781);
		 FNProbMap.put("police",0.013245033112582781);
		 FNProbMap.put("province",0.013245033112582781);
		 FNProbMap.put("operateurs",0.026490066225165563);
		 FNProbMap.put("toujours",0.026490066225165563);
		 FNProbMap.put("service",0.046357615894039736);
		 FNProbMap.put("sous",0.019867549668874173);
		 FNProbMap.put("commission.",0.013245033112582781);
		 FNProbMap.put("commission,",0.026490066225165563);
		 FNProbMap.put("parlementaires",0.013245033112582781);
		 FNProbMap.put("demander",0.013245033112582781);
		 FNProbMap.put("terre",0.013245033112582781);
		 FNProbMap.put("privees",0.013245033112582781);
		 FNProbMap.put("27",0.026490066225165563);
		 FNProbMap.put("23",0.013245033112582781);
		 FNProbMap.put("d'autres",0.013245033112582781);
		 FNProbMap.put("mardi",0.052980132450331126);
		 FNProbMap.put("21",0.013245033112582781);
		 FNProbMap.put("000",0.039735099337748346);
		 FNProbMap.put("20",0.013245033112582781);
		 FNProbMap.put("taches",0.013245033112582781);
		 FNProbMap.put("18",0.013245033112582781);
		 FNProbMap.put("quelque",0.013245033112582781);
		 FNProbMap.put("tournoi",0.013245033112582781);
		 FNProbMap.put("leur",0.06622516556291391);
		 FNProbMap.put("invites,",0.013245033112582781);
		 FNProbMap.put("ans",0.019867549668874173);
		 FNProbMap.put("responsabilites",0.013245033112582781);
		 FNProbMap.put("plus",0.07947019867549669);
		 FNProbMap.put("morts",0.013245033112582781);
		 FNProbMap.put("d'un",0.052980132450331126);
		 FNProbMap.put("public\",",0.013245033112582781);
		 FNProbMap.put("mission",0.026490066225165563);
		 FNProbMap.put("vers",0.013245033112582781);
		 FNProbMap.put("limites",0.013245033112582781);
		 FNProbMap.put("papiers",0.013245033112582781);
		 FNProbMap.put("clairement",0.013245033112582781);
		 FNProbMap.put("sont",0.059602649006622516);
		 FNProbMap.put("scruter",0.013245033112582781);
		 FNProbMap.put("trouve",0.013245033112582781);
		 FNProbMap.put("redevance.",0.013245033112582781);
		 FNProbMap.put("autorites",0.013245033112582781);
		 FNProbMap.put("\"La",0.013245033112582781);
		 FNProbMap.put("craignent",0.013245033112582781);
		 FNProbMap.put("pour",0.2119205298013245);	//interesting
		 FNProbMap.put("cite",0.013245033112582781);
		 FNProbMap.put("milliards",0.013245033112582781);
		 FNProbMap.put("\"Je",0.013245033112582781);
		 FNProbMap.put("soit",0.046357615894039736);
		 FNProbMap.put("professionnels",0.019867549668874173);
		 FNProbMap.put("gouvernement,",0.013245033112582781);
		 FNProbMap.put("\"Il",0.013245033112582781);
		 FNProbMap.put("Mais",0.019867549668874173);
		 FNProbMap.put("d'une",0.046357615894039736);
		 FNProbMap.put("Noah",0.019867549668874173);
		 FNProbMap.put("Nicolas",0.013245033112582781);
		 FNProbMap.put("UMP",0.019867549668874173);
		 FNProbMap.put("declaration",0.013245033112582781);
		 FNProbMap.put("compris",0.013245033112582781);
		 FNProbMap.put("dit",0.033112582781456956);
		 FNProbMap.put("(PS),",0.019867549668874173);
		 FNProbMap.put("demande",0.026490066225165563);
		 FNProbMap.put("cinq",0.013245033112582781);
		 FNProbMap.put("installes",0.013245033112582781);
		 FNProbMap.put("aurait",0.019867549668874173);
		 FNProbMap.put("apres",0.026490066225165563);
		 FNProbMap.put("Ainsi,",0.013245033112582781);
		 FNProbMap.put("l'Assemblee",0.013245033112582781);
		 FNProbMap.put("magnitude",0.013245033112582781);
		 FNProbMap.put("majorite",0.013245033112582781);
		 FNProbMap.put("des",0.26490066225165565);	//interesting
		 FNProbMap.put("Hoop",0.019867549668874173);
		 FNProbMap.put("commission",0.052980132450331126);
		 FNProbMap.put("avant",0.033112582781456956);
		 FNProbMap.put("Catherine",0.013245033112582781);
		 FNProbMap.put("aucun",0.013245033112582781);
		 FNProbMap.put("Des",0.019867549668874173);
		 FNProbMap.put("cout",0.013245033112582781);
		 FNProbMap.put("etudes",0.013245033112582781);
		 FNProbMap.put("aussi",0.026490066225165563);
		 FNProbMap.put("Cope.",0.026490066225165563);
		 FNProbMap.put("Cope,",0.013245033112582781);
		 FNProbMap.put("vendredi",0.013245033112582781);
		 FNProbMap.put("cote",0.013245033112582781);
		 FNProbMap.put("fois",0.013245033112582781);
		 FNProbMap.put("telecommunications",0.019867549668874173);
		 FNProbMap.put("Sichuan,",0.013245033112582781);
		 FNProbMap.put("s'est",0.019867549668874173);
		 FNProbMap.put("Christian",0.013245033112582781);
		 FNProbMap.put("suis",0.013245033112582781);
		 FNProbMap.put("Sarkozy,",0.013245033112582781);
		 FNProbMap.put("avait",0.059602649006622516);
		 FNProbMap.put("egalement",0.019867549668874173);
		 FNProbMap.put("sein",0.013245033112582781);
		 FNProbMap.put("Ki-moon",0.026490066225165563);
		 FNProbMap.put("KFOR",0.013245033112582781);
		 FNProbMap.put("personnes",0.013245033112582781);
		 FNProbMap.put("maniere",0.013245033112582781);
		 FNProbMap.put("Qingchuan,",0.013245033112582781);
		 FNProbMap.put("Vidal,",0.013245033112582781);
		 FNProbMap.put("etre",0.026490066225165563);
		 FNProbMap.put("inquietudes",0.013245033112582781);
		 FNProbMap.put("force",0.019867549668874173);
		 FNProbMap.put("d'euros",0.013245033112582781);
		 FNProbMap.put("France",0.039735099337748346);
		 FNProbMap.put("dirigeants",0.013245033112582781);
		 FNProbMap.put("Jean-Francois",0.013245033112582781);
		 FNProbMap.put("Patrick",0.013245033112582781);
		 FNProbMap.put("tous",0.019867549668874173);
		 FNProbMap.put("tour",0.013245033112582781);
		 FNProbMap.put("rapport",0.019867549668874173);
		 FNProbMap.put("soutien",0.013245033112582781);
		 FNProbMap.put("joue",0.019867549668874173);
		 FNProbMap.put("Apres",0.013245033112582781);
		 FNProbMap.put("va",0.013245033112582781);
		 FNProbMap.put("suite",0.019867549668874173);
		 FNProbMap.put("reunion",0.013245033112582781);
		 FNProbMap.put("un",0.17218543046357615);	//interesting
		 FNProbMap.put("mois",0.019867549668874173);
		 FNProbMap.put("devaient",0.013245033112582781);
		 FNProbMap.put("greve",0.013245033112582781);
		 FNProbMap.put("sur",0.1390728476821192);	//interesting
		 FNProbMap.put("redevance",0.033112582781456956);
		 FNProbMap.put("ceux",0.013245033112582781);
		 FNProbMap.put("quelques",0.019867549668874173);
		 FNProbMap.put("si",0.019867549668874173);
		 FNProbMap.put("se",0.059602649006622516);
		 FNProbMap.put("sa",0.046357615894039736);
		 FNProbMap.put("premiere",0.026490066225165563);
		 FNProbMap.put("hommes",0.013245033112582781);
		 FNProbMap.put("Societe",0.013245033112582781);
		 FNProbMap.put("ou",0.07947019867549669);
		 FNProbMap.put("on",0.039735099337748346);
		 FNProbMap.put("passe",0.019867549668874173);
		 FNProbMap.put("Dans",0.019867549668874173);
		 FNProbMap.put("partir",0.013245033112582781);
		 FNProbMap.put("ne",0.07947019867549669);
		 FNProbMap.put("son",0.039735099337748346);
		 FNProbMap.put("vais",0.019867549668874173);
		 FNProbMap.put("l'Institut",0.013245033112582781);
		 FNProbMap.put("me",0.013245033112582781);
		 FNProbMap.put("qu'un",0.019867549668874173);
		 FNProbMap.put("le",0.32450331125827814);	//interesting
		 FNProbMap.put("mai",0.019867549668874173);
		 FNProbMap.put("la",0.4966887417218543);	//interesting
		 FNProbMap.put("meme",0.039735099337748346);
		 FNProbMap.put("l'ancien",0.019867549668874173);
		 FNProbMap.put("femmes",0.019867549668874173);
		 FNProbMap.put("je",0.052980132450331126);
		 FNProbMap.put("cela",0.013245033112582781);
		 FNProbMap.put("contre",0.019867549668874173);
		 FNProbMap.put("pays",0.013245033112582781);
		 FNProbMap.put("il",0.039735099337748346);
		 FNProbMap.put("sans",0.033112582781456956);
		 FNProbMap.put("Thomas",0.013245033112582781);
		 FNProbMap.put("mobilisation",0.013245033112582781);
		 FNProbMap.put("menee",0.013245033112582781);
		 FNProbMap.put("president",0.033112582781456956);
		 FNProbMap.put("Plus",0.013245033112582781);
		 FNProbMap.put("secretaire",0.013245033112582781);
		 FNProbMap.put("d'avoir",0.013245033112582781);
		 FNProbMap.put("pas",0.08609271523178808);
		 FNProbMap.put("par",0.12582781456953643);	//interesting
		 FNProbMap.put("*)(*.",0.019867549668874173);
		 FNProbMap.put("Vers",0.013245033112582781);
		 FNProbMap.put("et",0.36423841059602646);	//interesting
		 FNProbMap.put("en",0.2052980132450331);	//interesting
		 FNProbMap.put("autres",0.033112582781456956);
		 FNProbMap.put("Par",0.013245033112582781);
		 FNProbMap.put("du",0.19205298013245034);	//interesting
		 FNProbMap.put("ses",0.046357615894039736);
		 FNProbMap.put("de",0.6887417218543046);	//interesting
		 FNProbMap.put("droit",0.046357615894039736);
		 FNProbMap.put("y",0.019867549668874173);
		 FNProbMap.put("Scheffer",0.013245033112582781);
		 FNProbMap.put("cet",0.033112582781456956);
		 FNProbMap.put("ces",0.026490066225165563);
		 FNProbMap.put("nous",0.052980132450331126);
		 FNProbMap.put("ce",0.07947019867549669);
		 FNProbMap.put("a",0.44370860927152317);	//interesting
		 FNProbMap.put("remise",0.013245033112582781);
		 FNProbMap.put("part",0.019867549668874173);
		 FNProbMap.put("d'amendement",0.013245033112582781);
		 FNProbMap.put("Yannick",0.013245033112582781);
		 FNProbMap.put("Ces",0.013245033112582781);
		 FNProbMap.put(":",0.026490066225165563);
		 FNProbMap.put("8",0.019867549668874173);
		 FNProbMap.put("tres",0.013245033112582781);
		 FNProbMap.put("au",0.2119205298013245);	//interesting
		 FNProbMap.put("tandis",0.013245033112582781);
		 FNProbMap.put("l'article",0.019867549668874173);
		 FNProbMap.put("toutes",0.013245033112582781);
		 FNProbMap.put("%",0.019867549668874173);
		 FNProbMap.put("qu'il",0.052980132450331126);
		 FNProbMap.put("Kosovo",0.019867549668874173);
		 FNProbMap.put("Serbie,",0.013245033112582781);
		 FNProbMap.put("Kosovo,",0.026490066225165563);
		 //FN Tokens: 151
		 //FRENCH Hashtable contains 1692 key value pair.
		 //------------------------------------------------
		 SPProbMap.put("participantes.",0.013986013986013986);
		 SPProbMap.put("mayo",0.013986013986013986);
		 SPProbMap.put("cayo",0.013986013986013986);
		 SPProbMap.put("puerta",0.013986013986013986);
		 SPProbMap.put("Defensa,",0.013986013986013986);
		 SPProbMap.put("tras",0.03496503496503497);
		 SPProbMap.put("Asamblea",0.013986013986013986);
		 SPProbMap.put("direccion",0.013986013986013986);
		 SPProbMap.put("tambien",0.04895104895104895);
		 SPProbMap.put("dicho",0.04195804195804196);
		 SPProbMap.put("este",0.04195804195804196);
		 SPProbMap.put("Federacion",0.013986013986013986);
		 SPProbMap.put("esta",0.08391608391608392);
		 SPProbMap.put("explosion",0.013986013986013986);
		 SPProbMap.put("Ferrero,",0.013986013986013986);
		 SPProbMap.put("detenidos",0.013986013986013986);
		 SPProbMap.put("autoridades.",0.013986013986013986);
		 SPProbMap.put("los",0.35664335664335667);	//interesting
		 SPProbMap.put("estado",0.013986013986013986);
		 SPProbMap.put("Smart",0.013986013986013986);
		 SPProbMap.put("dias",0.013986013986013986);
		 SPProbMap.put("investigados",0.013986013986013986);
		 SPProbMap.put("jefe",0.02097902097902098);
		 SPProbMap.put("estas",0.013986013986013986);
		 SPProbMap.put("Argentina",0.02097902097902098);
		 SPProbMap.put("Los",0.03496503496503497);
		 SPProbMap.put("Fe,",0.013986013986013986);
		 SPProbMap.put("granos",0.027972027972027972);
		 SPProbMap.put("pais",0.03496503496503497);
		 SPProbMap.put("estaba",0.013986013986013986);
		 SPProbMap.put("caso",0.04195804195804196);
		 SPProbMap.put("ultimos",0.013986013986013986);
		 SPProbMap.put("Despues",0.013986013986013986);
		 SPProbMap.put("Nobel",0.013986013986013986);
		 SPProbMap.put("detenido",0.013986013986013986);
		 SPProbMap.put("convocar",0.013986013986013986);
		 SPProbMap.put("primer",0.08391608391608392);
		 SPProbMap.put("comision",0.013986013986013986);
		 SPProbMap.put("principal",0.02097902097902098);
		 SPProbMap.put("acusacion",0.013986013986013986);
		 SPProbMap.put("concentracion",0.013986013986013986);
		 SPProbMap.put("siguiente",0.013986013986013986);
		 SPProbMap.put("Garros,",0.02097902097902098);
		 SPProbMap.put("actividades",0.013986013986013986);
		 SPProbMap.put("semana",0.02097902097902098);
		 SPProbMap.put("Judio",0.013986013986013986);
		 SPProbMap.put("160",0.013986013986013986);
		 SPProbMap.put("Un",0.013986013986013986);
		 SPProbMap.put("ayer",0.03496503496503497);
		 SPProbMap.put("mucho",0.02097902097902098);
		 SPProbMap.put("Agraria",0.013986013986013986);
		 SPProbMap.put("durante",0.013986013986013986);
		 SPProbMap.put("protagonizada",0.013986013986013986);
		 SPProbMap.put("productores",0.02097902097902098);
		 SPProbMap.put("general",0.02097902097902098);
		 SPProbMap.put("Su",0.013986013986013986);
		 SPProbMap.put("martes,",0.013986013986013986);
		 SPProbMap.put("funciones",0.013986013986013986);
		 SPProbMap.put("danos",0.013986013986013986);
		 SPProbMap.put("muestra",0.013986013986013986);
		 SPProbMap.put("gran",0.02097902097902098);
		 SPProbMap.put("Pese",0.02097902097902098);
		 SPProbMap.put("introduce",0.013986013986013986);
		 SPProbMap.put("ley",0.013986013986013986);
		 SPProbMap.put("Lopez",0.013986013986013986);
		 SPProbMap.put("les",0.02097902097902098);
		 SPProbMap.put("fabricacion",0.02097902097902098);
		 SPProbMap.put("comisario",0.013986013986013986);
		 SPProbMap.put("pasado",0.027972027972027972);
		 SPProbMap.put("sector",0.013986013986013986);
		 SPProbMap.put("impuesto",0.013986013986013986);
		 SPProbMap.put("martes",0.027972027972027972);
		 SPProbMap.put("negociaciones",0.013986013986013986);
		 SPProbMap.put("Otro",0.013986013986013986);
		 SPProbMap.put("algo",0.02097902097902098);
		 SPProbMap.put("autoridades",0.027972027972027972);
		 SPProbMap.put("producido",0.02097902097902098);
		 SPProbMap.put("109",0.02097902097902098);
		 SPProbMap.put("que,",0.013986013986013986);
		 SPProbMap.put("choque",0.013986013986013986);
		 SPProbMap.put("Congreso",0.013986013986013986);
		 SPProbMap.put("borrador",0.013986013986013986);
		 SPProbMap.put("previa,",0.02097902097902098);
		 SPProbMap.put("Estado",0.027972027972027972);
		 SPProbMap.put("contra,",0.013986013986013986);
		 SPProbMap.put("las",0.2517482517482518);	//interesting
		 SPProbMap.put("Israel",0.055944055944055944);
		 SPProbMap.put("compromiso",0.013986013986013986);
		 SPProbMap.put("sobre",0.06993006993006994);
		 SPProbMap.put("Lo",0.013986013986013986);
		 SPProbMap.put("mayo,",0.013986013986013986);
		 SPProbMap.put("Las",0.02097902097902098);
		 SPProbMap.put("La",0.07692307692307693);
		 SPProbMap.put("esa",0.027972027972027972);
		 SPProbMap.put("traves",0.013986013986013986);
		 SPProbMap.put("entender",0.013986013986013986);
		 SPProbMap.put("Ruano",0.02097902097902098);
		 SPProbMap.put("bomba",0.02097902097902098);
		 SPProbMap.put("intensas",0.013986013986013986);
		 SPProbMap.put("era",0.02097902097902098);
		 SPProbMap.put("federales",0.013986013986013986);
		 SPProbMap.put("centros",0.013986013986013986);
		 SPProbMap.put("pesan",0.013986013986013986);
		 SPProbMap.put("comienzo",0.013986013986013986);
		 SPProbMap.put("actuales",0.013986013986013986);
		 SPProbMap.put("violencia",0.013986013986013986);
		 SPProbMap.put("mientras",0.04895104895104895);
		 SPProbMap.put("uno",0.02097902097902098);
		 SPProbMap.put("una",0.2517482517482518);	//interesting
		 SPProbMap.put("inaceptables",0.013986013986013986);
		 SPProbMap.put("edicion",0.013986013986013986);
		 SPProbMap.put("fueron",0.02097902097902098);
		 SPProbMap.put("partido",0.03496503496503497);
		 SPProbMap.put("Es",0.02097902097902098);
		 SPProbMap.put("policia",0.027972027972027972);
		 SPProbMap.put("rey",0.02097902097902098);
		 SPProbMap.put("En",0.06993006993006994);
		 SPProbMap.put("El",0.2097902097902098);
		 SPProbMap.put("muerto",0.027972027972027972);
		 SPProbMap.put("muerte",0.013986013986013986);
		 SPProbMap.put("Dublin",0.02097902097902098);
		 SPProbMap.put("civiles\",",0.013986013986013986);
		 SPProbMap.put("informa",0.013986013986013986);
		 SPProbMap.put("minutos",0.013986013986013986);
		 SPProbMap.put("empresario",0.013986013986013986);
		 SPProbMap.put("conferencia",0.027972027972027972);
		 SPProbMap.put("hoy",0.055944055944055944);
		 SPProbMap.put("agentes",0.013986013986013986);
		 SPProbMap.put("procesos",0.02097902097902098);
		 SPProbMap.put("debe",0.02097902097902098);
		 SPProbMap.put("\"el",0.013986013986013986);
		 SPProbMap.put("ronda",0.013986013986013986);
		 SPProbMap.put("caso,",0.013986013986013986);
		 SPProbMap.put("Al",0.027972027972027972);
		 SPProbMap.put("aunque",0.03496503496503497);
		 SPProbMap.put("Nadal",0.027972027972027972);
		 SPProbMap.put("proceso",0.013986013986013986);
		 SPProbMap.put("horas",0.02097902097902098);
		 SPProbMap.put("fuerzas",0.027972027972027972);
		 SPProbMap.put("ataques",0.02097902097902098);
		 SPProbMap.put("aliados",0.013986013986013986);
		 SPProbMap.put("ellos",0.02097902097902098);
		 SPProbMap.put("lesion",0.02097902097902098);
		 SPProbMap.put("tipos",0.013986013986013986);
		 SPProbMap.put("favorito,",0.013986013986013986);
		 SPProbMap.put("Hollywood,",0.013986013986013986);
		 SPProbMap.put("numero",0.013986013986013986);
		 SPProbMap.put("militantes",0.013986013986013986);
		 SPProbMap.put("acuerdo",0.02097902097902098);
		 SPProbMap.put("\"causan",0.013986013986013986);
		 SPProbMap.put("Olmert,",0.03496503496503497);
		 SPProbMap.put("fosiles",0.013986013986013986);
		 SPProbMap.put("ademas",0.013986013986013986);
		 SPProbMap.put("puede",0.02097902097902098);
		 SPProbMap.put("cinco",0.027972027972027972);
		 SPProbMap.put("Pollack",0.013986013986013986);
		 SPProbMap.put("apoyo",0.027972027972027972);
		 SPProbMap.put("Santa",0.013986013986013986);
		 SPProbMap.put("quien",0.02097902097902098);
		 SPProbMap.put("elecciones.",0.013986013986013986);
		 SPProbMap.put("lleva",0.013986013986013986);
		 SPProbMap.put("entre",0.09090909090909091);
		 SPProbMap.put("protestas",0.013986013986013986);
		 SPProbMap.put("titular",0.013986013986013986);
		 SPProbMap.put("ante",0.06993006993006994);
		 SPProbMap.put("Para",0.013986013986013986);
		 SPProbMap.put("abril",0.013986013986013986);
		 SPProbMap.put("provincia",0.02097902097902098);
		 SPProbMap.put("localidades",0.013986013986013986);
		 SPProbMap.put("antes",0.013986013986013986);
		 SPProbMap.put("tarea",0.013986013986013986);
		 SPProbMap.put("sino",0.013986013986013986);
		 SPProbMap.put("vida",0.013986013986013986);
		 SPProbMap.put("bien",0.013986013986013986);
		 SPProbMap.put("provincias",0.013986013986013986);
		 SPProbMap.put("80",0.02097902097902098);
		 SPProbMap.put("mundo,",0.013986013986013986);
		 SPProbMap.put("destino",0.013986013986013986);
		 SPProbMap.put("permitio",0.013986013986013986);
		 SPProbMap.put("Roland",0.02097902097902098);
		 SPProbMap.put("grupos",0.02097902097902098);
		 SPProbMap.put("que",0.5944055944055944);	//interesting
		 SPProbMap.put("frances",0.013986013986013986);
		 SPProbMap.put("miembros",0.027972027972027972);
		 SPProbMap.put("afirma",0.02097902097902098);
		 SPProbMap.put("donde",0.027972027972027972);
		 SPProbMap.put("unos",0.02097902097902098);
		 SPProbMap.put("hay",0.02097902097902098);
		 SPProbMap.put("han",0.1258741258741259);	//interesting
		 SPProbMap.put("siete",0.013986013986013986);
		 SPProbMap.put("historica",0.013986013986013986);
		 SPProbMap.put("Tras",0.013986013986013986);
		 SPProbMap.put("anos",0.02097902097902098);
		 SPProbMap.put("prensa",0.02097902097902098);
		 SPProbMap.put("seguridad",0.013986013986013986);
		 SPProbMap.put("*)(*000",0.027972027972027972);
		 SPProbMap.put("millones",0.013986013986013986);
		 SPProbMap.put("fuera",0.013986013986013986);
		 SPProbMap.put("Este",0.013986013986013986);
		 SPProbMap.put("ellas",0.013986013986013986);
		 SPProbMap.put("mismo",0.02097902097902098);
		 SPProbMap.put("30",0.02097902097902098);
		 SPProbMap.put("ano,",0.013986013986013986);
		 SPProbMap.put("cualquier",0.02097902097902098);
		 SPProbMap.put("tratado",0.02097902097902098);
		 SPProbMap.put("Fuentes",0.013986013986013986);
		 SPProbMap.put("26",0.013986013986013986);
		 SPProbMap.put("25",0.013986013986013986);
		 SPProbMap.put("tiempo,",0.013986013986013986);
		 SPProbMap.put("20",0.013986013986013986);
		 SPProbMap.put("hubo",0.013986013986013986);
		 SPProbMap.put("11",0.013986013986013986);
		 SPProbMap.put("10",0.013986013986013986);
		 SPProbMap.put("1.",0.02097902097902098);
		 SPProbMap.put("aprobado",0.013986013986013986);
		 SPProbMap.put("asesor",0.013986013986013986);
		 SPProbMap.put("Estados",0.013986013986013986);
		 SPProbMap.put("argentino",0.02097902097902098);
		 SPProbMap.put("tanto",0.02097902097902098);
		 SPProbMap.put("Marc",0.013986013986013986);
		 SPProbMap.put("sido",0.04195804195804196);
		 SPProbMap.put("paises",0.027972027972027972);
		 SPProbMap.put("Gobierno",0.04195804195804196);
		 SPProbMap.put("ciudad",0.013986013986013986);
		 SPProbMap.put("estudiantes",0.027972027972027972);
		 SPProbMap.put("medida",0.02097902097902098);
		 SPProbMap.put("momento",0.027972027972027972);
		 SPProbMap.put("155,",0.013986013986013986);
		 SPProbMap.put("eran",0.013986013986013986);
		 SPProbMap.put("segundo",0.02097902097902098);
		 SPProbMap.put("segunda",0.027972027972027972);
		 SPProbMap.put("Martin",0.013986013986013986);
		 SPProbMap.put("6-3,",0.02097902097902098);
		 SPProbMap.put("porque",0.02097902097902098);
		 SPProbMap.put("dos",0.04895104895104895);
		 SPProbMap.put("embargo,",0.027972027972027972);
		 SPProbMap.put("Tambien",0.013986013986013986);
		 SPProbMap.put("solo",0.04195804195804196);
		 SPProbMap.put("despues",0.03496503496503497);
		 SPProbMap.put("dialogo",0.013986013986013986);
		 SPProbMap.put("vencio",0.013986013986013986);
		 SPProbMap.put("Gobierno.",0.013986013986013986);
		 SPProbMap.put("texto",0.013986013986013986);
		 SPProbMap.put("carreteras,",0.013986013986013986);
		 SPProbMap.put("jornada",0.013986013986013986);
		 SPProbMap.put("Unidos,",0.013986013986013986);
		 SPProbMap.put("mayoria",0.027972027972027972);
		 SPProbMap.put("asuntos",0.013986013986013986);
		 SPProbMap.put("Nicolas",0.013986013986013986);
		 SPProbMap.put("dejado",0.03496503496503497);
		 SPProbMap.put("Silberstein,",0.013986013986013986);
		 SPProbMap.put("encuentro",0.02097902097902098);
		 SPProbMap.put("posibilidad",0.013986013986013986);
		 SPProbMap.put("\"Olmert",0.013986013986013986);
		 SPProbMap.put("otros",0.013986013986013986);
		 SPProbMap.put("segun",0.04195804195804196);
		 SPProbMap.put("Ademas,",0.013986013986013986);
		 SPProbMap.put("dio",0.013986013986013986);
		 SPProbMap.put("\"Hemos",0.013986013986013986);
		 SPProbMap.put("discusiones",0.013986013986013986);
		 SPProbMap.put("dia",0.013986013986013986);
		 SPProbMap.put("Marrero,",0.013986013986013986);
		 SPProbMap.put("algunos",0.02097902097902098);
		 SPProbMap.put("2006.",0.013986013986013986);
		 SPProbMap.put("hasta",0.013986013986013986);
		 SPProbMap.put("nuevo",0.04195804195804196);
		 SPProbMap.put("sera",0.02097902097902098);
		 SPProbMap.put("\"Es",0.013986013986013986);
		 SPProbMap.put("policias",0.027972027972027972);
		 SPProbMap.put("\"El",0.027972027972027972);
		 SPProbMap.put("Siria",0.027972027972027972);
		 SPProbMap.put("oficial",0.013986013986013986);
		 SPProbMap.put("Hizbula",0.013986013986013986);
		 SPProbMap.put("nueva",0.013986013986013986);
		 SPProbMap.put("6-4",0.013986013986013986);
		 SPProbMap.put("6-3",0.013986013986013986);
		 SPProbMap.put("crisis",0.02097902097902098);
		 SPProbMap.put("presidente",0.027972027972027972);
		 SPProbMap.put("comercializacion",0.013986013986013986);
		 SPProbMap.put("escandalo",0.013986013986013986);
		 SPProbMap.put("del",0.32867132867132864);	//interesting
		 SPProbMap.put("Talansky",0.02097902097902098);
		 SPProbMap.put("lugar",0.013986013986013986);
		 SPProbMap.put("primera",0.013986013986013986);
		 SPProbMap.put("conocido",0.013986013986013986);
		 SPProbMap.put("investigacion",0.013986013986013986);
		 SPProbMap.put("Gyanendra",0.013986013986013986);
		 SPProbMap.put("trata",0.013986013986013986);
		 SPProbMap.put("personas,",0.013986013986013986);
		 SPProbMap.put("Virginia",0.013986013986013986);
		 SPProbMap.put("cuatro",0.04895104895104895);
		 SPProbMap.put("China,",0.013986013986013986);
		 SPProbMap.put("juego",0.027972027972027972);
		 SPProbMap.put("muy",0.013986013986013986);
		 SPProbMap.put("detencion",0.02097902097902098);
		 SPProbMap.put("tan",0.013986013986013986);
		 SPProbMap.put("cuando",0.04195804195804196);
		 SPProbMap.put("carteles",0.013986013986013986);
		 SPProbMap.put("redefinir",0.013986013986013986);
		 SPProbMap.put("Tal",0.013986013986013986);
		 SPProbMap.put("politica",0.02097902097902098);
		 SPProbMap.put("television",0.013986013986013986);
		 SPProbMap.put("decision",0.013986013986013986);
		 SPProbMap.put("mundo",0.03496503496503497);
		 SPProbMap.put("participantes",0.013986013986013986);
		 SPProbMap.put("pese",0.013986013986013986);
		 SPProbMap.put("pero",0.06293706293706294);
		 SPProbMap.put("equipos",0.013986013986013986);
		 SPProbMap.put("otro",0.03496503496503497);
		 SPProbMap.put("hace",0.03496503496503497);
		 SPProbMap.put("miles",0.013986013986013986);
		 SPProbMap.put("espanol",0.02097902097902098);
		 SPProbMap.put("India",0.013986013986013986);
		 SPProbMap.put("talibanes",0.013986013986013986);
		 SPProbMap.put("otras",0.027972027972027972);
		 SPProbMap.put("narcotrafico",0.013986013986013986);
		 SPProbMap.put("ya",0.04895104895104895);
		 SPProbMap.put("Segun",0.013986013986013986);
		 SPProbMap.put("clasifico",0.013986013986013986);
		 SPProbMap.put("racimo,",0.02097902097902098);
		 SPProbMap.put("como",0.11188811188811189);	//interesting
		 SPProbMap.put("alemana",0.013986013986013986);
		 SPProbMap.put("cuya",0.013986013986013986);
		 SPProbMap.put("golpe",0.013986013986013986);
		 SPProbMap.put("participacion",0.013986013986013986);
		 SPProbMap.put("Colombia",0.02097902097902098);
		 SPProbMap.put("hacer",0.02097902097902098);
		 SPProbMap.put("disputar",0.013986013986013986);
		 SPProbMap.put("va",0.02097902097902098);
		 SPProbMap.put("Feliciano",0.02097902097902098);
		 SPProbMap.put("tiene",0.027972027972027972);
		 SPProbMap.put("un",0.2867132867132867);	//interesting
		 SPProbMap.put("Siria.",0.013986013986013986);
		 SPProbMap.put("Lopez,",0.02097902097902098);
		 SPProbMap.put("ATP",0.013986013986013986);
		 SPProbMap.put("Partido",0.013986013986013986);
		 SPProbMap.put("legislativa",0.013986013986013986);
		 SPProbMap.put("Policia",0.013986013986013986);
		 SPProbMap.put("por",0.24475524475524477);	//interesting
		 SPProbMap.put("sus",0.0979020979020979);
		 SPProbMap.put("educacion",0.013986013986013986);
		 SPProbMap.put("funcionario",0.013986013986013986);
		 SPProbMap.put("ultimo",0.02097902097902098);
		 SPProbMap.put("su",0.1888111888111888);	//interesting
		 SPProbMap.put("si",0.04895104895104895);
		 SPProbMap.put("sigue",0.02097902097902098);
		 SPProbMap.put("se",0.3146853146853147);	//interesting
		 SPProbMap.put("Suu",0.013986013986013986);
		 SPProbMap.put("Por",0.013986013986013986);
		 SPProbMap.put("firmantes",0.013986013986013986);
		 SPProbMap.put("victimas",0.02097902097902098);
		 SPProbMap.put("Barak,",0.02097902097902098);
		 SPProbMap.put("menos",0.04195804195804196);
		 SPProbMap.put("manana",0.013986013986013986);
		 SPProbMap.put("bombas",0.04195804195804196);
		 SPProbMap.put("Rusia,",0.013986013986013986);
		 SPProbMap.put("cese",0.013986013986013986);
		 SPProbMap.put("ponerse",0.013986013986013986);
		 SPProbMap.put("Hamas",0.02097902097902098);
		 SPProbMap.put("David",0.02097902097902098);
		 SPProbMap.put("llegar",0.013986013986013986);
		 SPProbMap.put("personas",0.03496503496503497);
		 SPProbMap.put("conversaciones",0.013986013986013986);
		 SPProbMap.put("armas",0.02097902097902098);
		 SPProbMap.put("excepcion",0.013986013986013986);
		 SPProbMap.put("residencia",0.013986013986013986);
		 SPProbMap.put("alumbrar",0.013986013986013986);
		 SPProbMap.put("grupo",0.027972027972027972);
		 SPProbMap.put("partir",0.013986013986013986);
		 SPProbMap.put("no",0.18181818181818182);	//interesting
		 SPProbMap.put("Olmert",0.055944055944055944);
		 SPProbMap.put("ni",0.013986013986013986);
		 SPProbMap.put("anos,",0.013986013986013986);
		 SPProbMap.put("muertos",0.013986013986013986);
		 SPProbMap.put("son",0.03496503496503497);
		 SPProbMap.put("Medina",0.013986013986013986);
		 SPProbMap.put("premio",0.013986013986013986);
		 SPProbMap.put("-en",0.013986013986013986);
		 SPProbMap.put("con",0.27972027972027974);	//interesting
		 SPProbMap.put("bajo",0.02097902097902098);
		 SPProbMap.put("fue",0.06293706293706294);
		 SPProbMap.put("desde",0.055944055944055944);
		 SPProbMap.put("lo",0.06993006993006994);
		 SPProbMap.put("mas",0.14685314685314685);
		 SPProbMap.put("le",0.027972027972027972);
		 SPProbMap.put("la",0.5664335664335665);	//interesting
		 SPProbMap.put("confirmado",0.013986013986013986);
		 SPProbMap.put("disgustado",0.013986013986013986);
		 SPProbMap.put("Carlos",0.013986013986013986);
		 SPProbMap.put("Ehud",0.02097902097902098);
		 SPProbMap.put("uso,",0.013986013986013986);
		 SPProbMap.put("contra",0.027972027972027972);
		 SPProbMap.put("estos",0.013986013986013986);
		 SPProbMap.put("sin",0.06993006993006994);
		 SPProbMap.put("ha",0.3146853146853147);	//interesting
		 SPProbMap.put("oportunidades",0.013986013986013986);
		 SPProbMap.put("cientificos",0.013986013986013986);
		 SPProbMap.put("cerca",0.013986013986013986);
		 SPProbMap.put("racimo",0.02097902097902098);
		 SPProbMap.put("Sin",0.02097902097902098);
		 SPProbMap.put("Israel,",0.013986013986013986);
		 SPProbMap.put("todo",0.04195804195804196);
		 SPProbMap.put("civiles.",0.013986013986013986);
		 SPProbMap.put("paz",0.027972027972027972);
		 SPProbMap.put("reeleccion",0.013986013986013986);
		 SPProbMap.put("ex",0.02097902097902098);
		 SPProbMap.put("es",0.1048951048951049);	//interesting
		 SPProbMap.put("hecho",0.013986013986013986);
		 SPProbMap.put("podrian",0.013986013986013986);
		 SPProbMap.put("afectado",0.013986013986013986);
		 SPProbMap.put("en",0.6013986013986014);	//interesting
		 SPProbMap.put("el",0.5874125874125874);	//interesting
		 SPProbMap.put("definitivamente",0.013986013986013986);
		 SPProbMap.put("brasileno",0.013986013986013986);
		 SPProbMap.put("heridos",0.013986013986013986);
		 SPProbMap.put("Ejecutivo",0.013986013986013986);
		 SPProbMap.put("fuentes",0.013986013986013986);
		 SPProbMap.put("atentados",0.013986013986013986);
		 SPProbMap.put("set",0.02097902097902098);
		 SPProbMap.put("ser",0.013986013986013986);
		 SPProbMap.put("de",0.8531468531468531);	//interesting
		 SPProbMap.put("y",0.5174825174825175);	//interesting
		 SPProbMap.put("Culiacan,",0.013986013986013986);
		 SPProbMap.put("o",0.03496503496503497);
		 SPProbMap.put("buscar",0.013986013986013986);
		 SPProbMap.put("lider",0.013986013986013986);
		 SPProbMap.put("paso",0.02097902097902098);
		 SPProbMap.put("torneo",0.02097902097902098);
		 SPProbMap.put("a",0.43356643356643354);	//interesting
		 SPProbMap.put("mantiene",0.02097902097902098);
		 SPProbMap.put("incidente,",0.013986013986013986);
		 SPProbMap.put("importante",0.013986013986013986);
		 SPProbMap.put("ministro",0.08391608391608392);
		 SPProbMap.put("fase",0.013986013986013986);
		 SPProbMap.put("A",0.02097902097902098);
		 SPProbMap.put("Torres,",0.013986013986013986);
		 SPProbMap.put("para",0.21678321678321677);
		 SPProbMap.put("Nacional",0.013986013986013986);
		 SPProbMap.put("tres",0.027972027972027972);
		 SPProbMap.put("al",0.20279720279720279);
		 SPProbMap.put("droga",0.013986013986013986);
		 SPProbMap.put("pais.",0.013986013986013986);
		 SPProbMap.put("pais,",0.027972027972027972);
		 SPProbMap.put("celebrado",0.02097902097902098);
		 //SP Tokens: 143
		 //SPANISH Hashtable contains 1988 key value pair.

	 }
	 public void SentenceFilter(){
		 //Extract tokens from the input scentence
		 double Prob, ENLikelihood, FNLikelihood, SPLikelihood;
		 ENLikelihood = FNLikelihood = SPLikelihood = 1.0/3;
		 System.out.println("Initial liklihood: " + ENLikelihood);
		 String Token;

		 st = new StringTokenizer(nameField.getText().toLowerCase());
		 //Read the appropiate prob from the hashtable
		 while (st.hasMoreTokens()) {
			 //Read the tokens from the input scentence
			Token = st.nextToken();
			//First check against EN Corpus
			if( ENProbMap.containsKey(Token) ){
				Prob = ENProbMap.get(Token);
				System.out.println("EN Corpus contains this token: (Token, Prob) --> (" + Token + ", " + format( Prob, 2, -3) + ")");
			    ENLikelihood = ENLikelihood*Prob;
	 		    System.out.println("EN liklihood: " + ENLikelihood);
			}else{
				System.out.println("EN Corpus does not contain <" +  Token + ">.");
				ENLikelihood = ENLikelihood*0.01;
	 		    System.out.println("EN liklihood: " + ENLikelihood);
			}

			//Secondly, check against FN Corpus
			if( FNProbMap.containsKey(Token) ){
				Prob = FNProbMap.get(Token);
				System.out.println("FN Corpus contains this token: (Token, Prob) --> (" + Token + ", " + format( Prob, 2, -3) + ")");
			    FNLikelihood = FNLikelihood*Prob;
			}else{
				System.out.println("FN Corpus does not contain <" +  Token + ">.");
				FNLikelihood = FNLikelihood*0.01;
			}

			//And finally check against SP Corpus
			if( SPProbMap.containsKey(Token) ){
				Prob = SPProbMap.get(Token);
				System.out.println("SP Corpus contains this token: (Token, Prob) --> (" + Token + ", " + format( Prob, 2, -3) + ")");
			    SPLikelihood = SPLikelihood*Prob;
			}else{
				System.out.println("SP Corpus does not contain <" +  Token + ">.");
				SPLikelihood = SPLikelihood*0.01;
			}
		}//end of analyzing all of the likelihood

		System.out.println("P(W1|EN)...P(Wk|EN)P(EN): "  + ENLikelihood );
		System.out.println("P(W1|FN)...P(Wk|FN)P(FN): "  + FNLikelihood );
		System.out.println("P(W1|SP)...P(Wk|SP)P(SP): "  + SPLikelihood );
		//Final calculation for likelihood
		//EN/FN/SP likelihood calculation
		ENLikelihoodFN = (ENLikelihood)/(ENLikelihood + FNLikelihood + SPLikelihood);
		FNLikelihoodFN = (FNLikelihood)/(ENLikelihood + FNLikelihood + SPLikelihood);
		SPLikelihoodFN = (SPLikelihood)/(ENLikelihood + FNLikelihood + SPLikelihood);

		System.out.println("EN likelihood: "  + ENLikelihoodFN );
		System.out.println("FN likelihood: "  + FNLikelihoodFN );
		System.out.println("SP likelihood: "  + SPLikelihoodFN );

	 }

	 static String format( double val, int n, int w)
	 	{
	 	//	rounding
	 		double incr = 0.5;
	 		for( int j=n; j>0; j--) incr /= 10;
	 		val += incr;

	 		String s = Double.toString(val);
	 		int n1 = s.indexOf('.');
	 		int n2 = s.length() - n1 - 1;

	 		if (n>n2)      s = s+ZEROES.substring(0, n-n2);
	 		else if (n2>n) s = s.substring(0,n1+n+1);

	 		if( w>0 & w>s.length() ) s = BLANKS.substring(0,w-s.length()) + s;
	 		else if ( w<0 & (-w)>s.length() ) {
	 			w=-w;
	 			s = s + BLANKS.substring(0,w-s.length()) ;
	 		}
	 		return s;
	}
}
