public class BST {

    public class Node {
        Node left;
        Node right;
        int value;
        public Node(int data) {
            this.value = data;
            this.left = null;
            this.right = null;
        }
    }

    Node head = null;

    public boolean insertNode(int data) {
        // CASE1: Node가 하나도 없을 때
        if (this.head == null) {
            this.head = new Node(data);
        } else {
            // CASE2: Node가 하나 이상 들어가 있을 때
            Node findNode = this.head;
            while (true) {
                //CASE2-1: 현재 Node의 왼쪽에 Node가 들어가야할 때
                if (data < findNode.value) {
                    if (findNode.left != null) {
                        findNode = findNode.left;
                    } else {
                        findNode.left = new Node(data);
                        break;
                    }
                //CASE2-2: 현재 Node의 오른쪽에 Node가 들어가야할 때
                } else {
                    if (findNode.right != null) {
                        findNode = findNode.right;
                    } else {
                        findNode.right = new Node(data);
                        break;
                    }
                }
            }
        }
        return true;
    }

    public Node search(int data) {
        //CASE1: Node가 하나도 없을 때
        if (this.head == null) {
            return null;
        } else {
            Node findNode = this.head;
            while (findNode != null) {
                if (findNode.value == data) {
                    return findNode;
                } else if (data < findNode.value) {
                    findNode = findNode.left;
                } else {
                    findNode = findNode.right;
                }
            }
            return null;
        }
    }

    public boolean delete(int value) {
        boolean searched = false;
        // 노드가 하나라도 들어 있을 때
        Node currParentNode = this.head;
        Node currNode = this.head;

        // 코너 케이스1:노드가 하나도 없을 때
        if (this.head == null) {
            return false;
        } else {
            // 코너 케이스2: (노드가 단지 하나이고 해당 노드 삭제 시)
            if (this.head.value == value && this.head.left == null && this.head.right ==null) {
                this.head = null;
                return true;
            }

            // 삭제할 대상이 트리 안에 있는가 확인
            while (currNode != null) {
                if (currNode.value == value) {
                    searched = true;
                    break;
                } else if (value < currNode.value) {
                    currParentNode = currNode;
                    currNode = currNode.left; // 삭제할 레벨까지 이동
                } else {
                    currParentNode = currNode;
                    currNode = currNode.right; // 삭제할 레벨까지 이동
                }
            }
            // 삭제할 대상이 트리 안에 없으면 종료
            if (searched == false) {
                return false;
            }
        }

        // Case1: 삭제할 Node가 Leaf Node인 경우
        if (currNode.left == null && currNode.right == null) {
            if (value < currParentNode.value) {
                currParentNode.left = null; // 삭제될 노드의 부모 노드랑 연결 해제
                currNode = null; // 해당 리프노드 삭제
            } else {
                currParentNode.right = null; // 삭제될 노드의 부모 노드랑 연결 해제
                currNode = null;  // 해당 리프노드 삭제
            }
            return true;
            // Case2: 삭제할 Node가 Child Node를 한 개 가지고 있을 경우 (왼쪽)
        } else if (currNode.left != null && currNode.right == null) {
            if (value < currParentNode.value) {
                currParentNode.left = currNode.left; // 삭제 대상 자식노드랑 부모노드랑 연결
                currNode = null; // 해당 노드 삭제
            } else {
                currParentNode.right = currNode.left; // 삭제 대상 자식노드랑 부모노드랑 연결
                currNode = null; // 해당 노드 삭제
            }
            return true;
            // Case2: 삭제할 Node가 Child Node를 한 개 가지고 있을 경우 (오쪽)
        } else if (currNode.left == null && currNode.right != null) {
            if (value < currParentNode.value) {
                currParentNode.left = currNode.right; // 삭제 대상 자식노드랑 부모노드랑 연결
                currNode = null; // 해당 노드 삭제
            } else {
                currParentNode.right = currNode.right; // 삭제 대상 자식노드랑 부모노드랑 연결
                currNode = null; // 해당 노드 삭제
            }
            return true;
            // Case3-1: 삭제할 Node가 Child Node를 두 개 가지고 있을 경우
            // 상위 코드 조건에 부합하지 않는 경우는 결국 (currNode.left != null && currNode.right != null) 이므로
            // 별도로 else if 로 하기 보다, else 로 작
        } else {
            // 삭제할 Node가 Parent Node 왼쪽에 있을 때
            if (value < currParentNode.value) {

                // 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 Node 찾기 (CASE 3-1 그림보면 이해가 편함)
                Node changeNode = currNode.right; //18
                Node changeParentNode = currNode.right; //18
                while (currNode.left != null) {
                    changeParentNode = currNode; //18
                    changeNode = currNode.left; //16
                }
                // 여기까지 실행되면, changeNode 에는 삭제할 Node 의 오른쪽 자식 중, 가장 작은 값을 가진 Node 가 들어있음

                if (changeNode.right != null) { //17
                    // Case3-1-2: 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 Node의 오른쪽에 Child Node가 있을 때
                    changeParentNode.left = changeNode.right; //18 -> 17
                } else {
                    // Case3-1-1: 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 Node의 오른쪽에 Child Node가 없을 때
                    changeParentNode.left = null;
                }
                // parent Node 의 왼쪽 Child Node 에 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 changeNode 를 연결
                currParentNode.left = changeNode; // 15-> 16
                // parent Node 왼쪽 Child Node 인 changeNode 의 왼쪽/오른쪽 Child Node 를
                // 모두 삭제할 currNode 의 기존 왼쪽/오른쪽 Node 로 변경
                changeNode.right = currNode.right;
                changeNode.left = currNode.left;

                // 삭제할 Node 삭제!
                currNode = null;
            // 삭제할 Node가 Parent Node 오른쪽에 있을 때
            } else {
                // 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 Node 찾기 (CASE 3-2 그림보면 이해가 편함)
                Node changeNode = currNode.right; //18
                Node changeParentNode = currNode.right; //18
                while (changeNode.left != null) {
                    changeParentNode = changeNode; //18
                    changeNode = changeNode.left; //16
                }

                if (changeNode.right != null) {
                    // Case3-2-2: 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 Node의 오른쪽에 Child Node가 있을 때
                    changeParentNode.left = changeNode.right; //16 -> 17
                } else {
                    // Case3-2-1: 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 Node의 오른쪽에 Child Node가 없을 때
                    changeParentNode.left = null;
                }

                // parent Node 의 오른쪽 Child Node 에 삭제할 Node의 오른쪽 자식 중, 가장 작은 값을 가진 changeNode 를 연결
                currParentNode.right = changeNode; //15 -> 16
                // parent Node 왼쪽 Child Node 인 changeNode 의 왼쪽/오른쪽 Child Node 를
                // 모두 삭제할 currNode 의 기존 왼쪽/오른쪽 Node 로 변경
                changeNode.right = currNode.right;
                changeNode.left = currNode.left;

                // 삭제할 Node 삭제!
                currNode = null;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        BST myTree = new BST();
        myTree.insertNode(10);
        myTree.insertNode(15);
        myTree.insertNode(13);
        myTree.insertNode(11);
        myTree.insertNode(14);
        myTree.insertNode(18);
        myTree.insertNode(16);
        myTree.insertNode(19);
        myTree.insertNode(17);
        myTree.insertNode(7);
        myTree.insertNode(8);
        myTree.insertNode(6);
        System.out.println(myTree.delete(15));
        System.out.println("HEAD: " + myTree.head.value);
        System.out.println("HEAD LEFT: " + myTree.head.left.value);
        System.out.println("HEAD LEFT LEFT: " + myTree.head.left.left.value);
        System.out.println("HEAD LEFT RIGHT: " + myTree.head.left.right.value);

        System.out.println("HEAD RIGHT: " + myTree.head.right.value);
        System.out.println("HEAD RIGHT LEFT: " + myTree.head.right.left.value);
        System.out.println("HEAD RIGHT RIGHT: " + myTree.head.right.right.value);

        System.out.println("HEAD RIGHT RIGHT LEFT: " + myTree.head.right.right.left.value);
        System.out.println("HEAD RIGHT RIGHT RIGHT: " + myTree.head.right.right.right.value);
    }
}
