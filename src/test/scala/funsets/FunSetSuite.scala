package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
  */

@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  import FunSets._

  test("contains implemented") {
    assert(contains(x => true, 100))
  }

  trait TestSets {
    val s1 = singletonSet(0)
    val s2 = (x: Int) => x > 0
    val s3 = (x: Int) => x % 2 == 0
    val s4 = (x: Int) => (x > -10) && (x < 10)
  }

  test("contains test") {
    new TestSets {
      assert(contains(s1, 0), "contains 1 + singleton 1")
      assert(!contains(s1, 10), "contains 2 + singleton 2")

      assert(contains(s2, 1), "contains 3")
      assert(!contains(s2, -2), "contains 4")

      assert(contains(s3, 222), "contains 5")
      assert(!contains(s3, 221), "contains 6")

      assert(contains(s4, 0), "contains 7")
      assert(!contains(s4, -11), "contains 8")
    }
  }

  test("union test") {
    new TestSets {
      val s1_s2 = union(s1, s2)
      val s2_s3 = union(s2, s3)
      val s3_s4 = union(s3, s4)
      val s2_s4 = union(s2, s4)

      assert(contains(s1_s2, 0), "Union 1")
      assert(contains(s1_s2, 2), "Union 2")
      assert(!contains(s1_s2, -1), "Union 3")

      assert(contains(s2_s3, -4), "Union 4")
      assert(contains(s2_s3, 2), "Union 5")
      assert(!contains(s2_s3, -15), "Union 6")

      assert(contains(s3_s4, 12), "Union 7")
      assert(contains(s3_s4, -5), "Union 8")
      assert(!contains(s3_s4, -13), "Union 9")

      assert(contains(s2_s4, -9), "Union 10")
      assert(contains(s2_s4, 20), "Union 11")
      assert(!contains(s2_s4, -20), "Union 12")
    }
  }

  test("intersect test") {
    new TestSets {
      val s1_s4 = intersect(s1, s4)
      val s2_s3 = intersect(s2, s3)
      val s3_s4 = intersect(s3, s4)
      val s2_s4 = intersect(s2, s4)

      assert(contains(s1_s4, 0), "Intersect 1")
      assert(!contains(s1_s4, -1), "Intersect 2")

      assert(contains(s2_s3, 2), "Intersect 3")
      assert(contains(s2_s3, 222), "Intersect 4")
      assert(!contains(s2_s3, -2), "Intersect 5")

      assert(contains(s3_s4, 8), "Intersect 6")
      assert(contains(s3_s4, -6), "Intersect 7")
      assert(!contains(s3_s4, 14), "Intersect 8")

      assert(contains(s2_s4, 3), "Intersect 9")
      assert(contains(s2_s4, 9), "Intersect 10")
      assert(!contains(s2_s4, 0), "Intersect 11")
    }
  }

  test("diff test") {
    new TestSets {
      val s2_s3 = diff(s2, s3)
      val s2_s4 = diff(s2, s4)

      assert(contains(s2_s4, 10), "Diff 1")
      assert(!contains(s2_s4, 5), "Diff 2")

      assert(contains(s2_s3, 1), "Diff 3")
      assert(contains(s2_s3, 15), "Diff 4")
      assert(!contains(s2_s3, 2), "Diff 5")
      assert(!contains(s2_s3, -3), "Diff 6")
    }
  }

  test("filter test") {
    new TestSets {
      val s2_filter = filter(s2, (x : Int) => x % 3 == 0)
      val s3_filter = filter(s3, (x : Int) => x < 10)
      val s4_filter = filter(s4, (x : Int) => x != 5)

      assert(contains(s2_filter, 9), "Filter 1")
      assert(contains(s2_filter, 27), "Filter 2")
      assert(!contains(s2_filter, -3), "Filter 3")
      assert(!contains(s2_filter, 13), "Filter 4")

      assert(contains(s3_filter, -2), "Filter 5")
      assert(!contains(s3_filter, 3), "Filter 6")
      assert(!contains(s3_filter, 12), "Filter 7")

      assert(contains(s4_filter, 2), "Filter 8")
      assert(!contains(s4_filter, 5), "Filter 9")
      assert(!contains(s4_filter, 11), "Filter 10")
    }
  }

  test("foreall test") {
    new TestSets {
      assert(forall(s3, (x : Int) => contains(s3, x)), "forall 1")
      assert(!forall(s2, (x : Int) => x < 10), "forall 2")
      assert(!forall(s3, (x : Int) => x % 3 == 0), "forall 3")
    }
  }

  test("exists test") {
    new TestSets {
      assert(exists(s2, (x : Int) => x == 999), "exists 1")
      assert(exists(s3, (x : Int) => x % 4 == 0), "exists 2")
      assert(exists(s4, (x : Int) => contains(s4, x)), "exists 3")

      assert(!exists(s2, (x : Int) => x == 0), "exists 4")
      assert(!exists(s3, (x : Int) => x % 2 == 1), "exists 5")
      assert(!exists(s4, (x : Int) => x > 10), "exists 6")
    }
  }

  test("map test") {
    new TestSets {
      val s1_map = map(s1, (x: Int) => x + 1)
      val s2_map = map(s2, (x: Int) => -x * 2)
      val s3_map = map(s3, (x: Int) => x * 4)
      val s4_map = map(s4, (x: Int) => x * 10)

      assert(contains(s1_map, 1), "map 1")
      assert(!contains(s1_map, 0), "map 2")
      assert(!contains(s1_map, -1), "map 3")

      assert(contains(s2_map, -8), "map 4")
      assert(contains(s2_map, -222), "map 5")
      assert(!contains(s2_map, 20), "map 6")

      assert(contains(s3_map, 8), "map 7")
      assert(contains(s3_map, 64), "map 8")
      assert(!contains(s3_map, 4), "map 9")

      assert(contains(s4_map, 90), "map 10")
      assert(contains(s4_map, -60), "map 11")
      assert(!contains(s4_map, 32), "map 12")
    }
  }
}