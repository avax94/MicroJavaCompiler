package rs.ac.bg.etf.pp1.sm130075d;
import java_cup.runtime.*;
%%
%class Lexer
%column
%line
%cup
%function next_token
%type java_cup.runtime.Symbol
%eofval{
  return new Symbol(sym.EOF);
%eofval}

%{
  private Symbol token(int type) {
    return new Symbol(type, yyline + 1, yycolumn, yytext());
  }
  
  private Symbol error() {
    return new Symbol(sym.error, yyline + 1, yycolumn, yytext());
  } 
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\b\f]
Comment = "//" {InputCharacter}* {LineTerminator}?

Ident = [:jletter:] ([:jletter:] | [:jletterdigit:] | "_")*
IntegerConst = [0-9]([0-9])*
BooleanConst = "true" | "false"
CharConst = \'.\'
MulOpLeft = "*" | "/" | "%"
Assign = "="
MulOpRight = {MulOpLeft}{Assign}
Minus = "-"
Plus  = "+"
AddOpRight = ({Plus}|{Minus}){Assign}
Relop = "==" | "!=" | "<" | "<=" | ">" | ">="
Dot = "\."
SColin = ";"
DPlus = "++"
DMinus = "--"
And = "&&"
Or = "||"
Colin = ","
Lbr = "("
Rbr = ")"
Lsbr = "["
Rsbr = "]"
Lcbr = "{"
Rcbr = "}"

%%

"const"    { return token(sym.CONST); }
"class"    { return token(sym.CLASS); }
"extends"  { return token(sym.EXTENDS); }
"static"   { return token(sym.STATIC); }
"void"     { return token(sym.VOID); }
"if" 	   { return token(sym.IF); }
"else"     { return token(sym.ELSE); }
"for"      { return token(sym.FOR); }
"break"    { return token(sym.BREAK); }
"continue" { return token(sym.CONTINUE); }
"return"   { return token(sym.RETURN); }
"print"    { return token(sym.PRINT); }
"read"     { return token(sym.READ); }
"program"  { return token(sym.PROGRAM); }
"new"      { return token(sym.NEW); }

{MulOpLeft}     { return token(sym.MULOPLEFT); }
{MulOpRight}    { return token(sym.MULOPRIGHT); }
{Minus}         { return token(sym.MINUS); }
{Plus}          { return token(sym.PLUS); }
{AddOpRight}    { return token(sym.ADDOPRIGHT); }
{Relop}   	{ return token(sym.RELOP); }
{Assign}	{ return token(sym.ASSIGN); }
{Dot}		{ return token(sym.DOT); }
{SColin}	{ return token(sym.SCOLIN); }
{DPlus}		{ return token(sym.DPLUS); }
{DMinus} 	{ return token(sym.DMINUS); }
{And}		{ return token(sym.AND); }
{Or}		{ return token(sym.OR); }
{Colin}		{ return token(sym.COLIN); }
{Lbr}		{ return token(sym.LBR); }
{Rbr}		{ return token(sym.RBR); }
{Lsbr}		{ return token(sym.LSBR); }
{Rsbr}		{ return token(sym.RSBR); }
{Lcbr}		{ return token(sym.LCBR); }
{Rcbr}		{ return token(sym.RCBR); }

{IntegerConst}	{ return token(sym.NUMCONST); }
{BooleanConst}	{ return token(sym.BOOLCONST); }
{CharConst}	{ return token(sym.CHARCONST); }

{Ident}		{ return token(sym.IDENT); }
{Comment}	{ }

{WhiteSpace}    { }
[^]  		{ return error(); }

