#include <fstream>
#include <iostream>
std::ofstream out("out.txt");
inline void generate( char* nthChar, int nth = 1 )
{
  if ( nth == 7 )
  {
    out.write( nthChar - 7, 9 );
    return;
  }

  for ( *nthChar = 97; *nthChar < 123; ++*nthChar )
  {
    generate( nthChar + 1, nth + 1 );
  }
}

int main()
{
  char arr[ 9 ]{};
  arr[ 8 ] = '\n';
  for ( char c = 'b'; c < 'm'; c += 2 )
  {
    std::cout << c << std::endl;
    arr[ 0 ] = c;
    arr[ 7 ] = c + 1;
    generate( arr + 1, 1 );
  }
}