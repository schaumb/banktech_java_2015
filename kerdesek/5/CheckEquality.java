public class CheckEquality
{
    public static void main(String[] args)
    {
    	Integer i1 = 64;
    	Integer i2 = Integer.valueOf( 64 );
    	Integer i3 = 256;
    	Integer i4 = Integer.valueOf( 256 );

    	Long l1 = 64L;
    	Long l2 = Long.valueOf( 64 );
    	Long l3 = 256L;
    	Long l4 = Long.valueOf( 256 );

    	System.out.println( i1 == i2 );
    	System.out.println( i3 == i4 );
    	System.out.println( l1 == l2 );
    	System.out.println( l3 == l4 );
    }
}