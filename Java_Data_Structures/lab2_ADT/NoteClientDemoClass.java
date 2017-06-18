

public class NoteClientDemoClass{
	public static void main(String[] args)throws Exception{
	NoteDataStructureClass note1=new NoteDataStructureClass();
	System.out.println(note1.toString());

	NoteDataStructureClass note2=new NoteDataStructureClass(8, -3);
	System.out.println(note2.toString());

	NoteDataStructureClass note3=new NoteDataStructureClass();
	System.out.println(note3.toString());

	NoteDataStructureClass note4=new NoteDataStructureClass(4,-6);
	System.out.println(note4.toString());

	}
}

