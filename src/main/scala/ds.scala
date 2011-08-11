package ds

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B,A) => B): B
}

object FoldLeft {
  implicit object FoldLeftList extends FoldLeft[List] {
    def foldLeft[A, B](xs: List[A], b: B, f: (B,A) => B) = xs.foldLeft(b)(f)
  }
}

trait Monoid[A] {
  def nappend(a1: A, a2: A): A
  def nzero: A
}

object Monoid {
  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    def nappend(a: Int, b: Int): Int = a + b
    def nzero: Int = 0
  }
  implicit object StringMonoid extends Monoid[String] {
    def nappend(a: String, b: String): String = a + b
    def nzero: String = ""
  }
}

trait Identity[A] {
  val value: A

  def |+|(a2: A)(implicit m: Monoid[A]): A = m.nappend(value, a2)
}

trait MA[M[_], A] {
  val value: M[_]

  def 
}

object Main {

  implicit def toIdent[A](a: A): Identity[A] = new Identity[A] {
    val value = a
  }
    

  val multMonoid = new Monoid[Int] {
    def nappend(a: Int, b: Int): Int = a*b
    def nzero: Int = 1
  }

  def sum[M[_], T](xs: M[T])(implicit m: Monoid[T], f1: FoldLeft[M]): T = f1.foldLeft(xs, m.nzero, m.nappend)

  def plus[T](a: T, b: T)(implicit m: Monoid[T]): T = m.nappend(a, b)

  def p(a: Any) { println("###> " + a) }
  def main(args: Array[String]) {
    println

    //p(sum(List(1,2,3,4)))
    //p(sum(List("a", "b", "c")))
    //p(sum(List(1,2,3,4))(multMonoid))
    p(3 |+| 4)

    println
  }
}