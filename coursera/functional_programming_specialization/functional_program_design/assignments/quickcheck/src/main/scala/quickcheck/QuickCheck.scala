package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    n <- arbitrary[A]
    h <- frequency((1, empty), (9, genHeap))
  } yield insert(n, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("findMin") = forAll { (n1: A, n2: A) =>
    // If we insert a node into an empty heap,
    // and then insert another node, the resulting heap
    // should return the smallest of the two original nodes
    // if we call findMin on it.
    val inserted: H = insert(n1, insert(n2, empty))
    findMin(inserted) == min(n1, n2)
  }

  property("last delete gives empty") = forAll { (n: A) =>
    // If we delete the smallest node in a heap
    // that only consists of one node we should get an empty node.
    isEmpty(deleteMin(insert(n, empty)))
  }

  property("check that heap is sorted") = forAll { (originalHeap: H) =>
    def isSorted(originalHeap: H): Boolean =
      if (isEmpty(originalHeap)) true
      else {
        val smallestNode: Int = findMin(originalHeap)
        val heapWithoutSmallestNode: H = deleteMin(originalHeap)
        isEmpty(heapWithoutSmallestNode) || (smallestNode <= findMin(heapWithoutSmallestNode) && isSorted(heapWithoutSmallestNode))
      }
    isSorted(originalHeap)
  }

  property("smallest is always smallest") = forAll { (heap1: H, heap2: H) =>
    // Finding the smallest node in two melded heaps is
    // the same as finding the smallest of the each heap
    // and then selecting the smallest of the two.
    findMin(meld(heap1, heap2)) == min(findMin(heap1), findMin(heap2))
  }

  property("melding is independent of symmetric manipulations") = forAll { (heap1: H, heap2: H) =>
    // Moving one node from one node to the other and melding them
    // generates the same melded heap as if we melded the original
    // heaps. We define equal heaps by having the same smallest node
    // and that the state of the heap is the same is the same if we
    // delete the smallest node from both heaps. Also same if both empty.
    def heapEqual(heap1: H, heap2: H): Boolean =
      if (isEmpty(heap1) && isEmpty(heap2)) true
      else {
        val smallestNode1 = findMin(heap1)
        val smallestNode2 = findMin(heap2)
        smallestNode1 == smallestNode2 && heapEqual(deleteMin(heap1), deleteMin(heap2))
      }

    val meld1: H = meld(heap1, heap2)
    val meld2: H = meld(deleteMin(heap1), insert(findMin(heap1), heap2))
    heapEqual(meld1, meld2)
  }

}