// Stack log hoạt động (tự cài bằng mảng)
package modules.digitallibrary;

public class StackLog {
    private String[] data;
    private int top = -1;

    public StackLog(int capacity) {
        data = new String[capacity];
    }

    public void push(String s) {
        if (top == data.length - 1) return; // đầy thì bỏ qua (có thể nâng cấp: ghi đè vòng tròn)
        data[++top] = s;
    }

    public void printLogs() {
        if (top == -1) {
            System.out.println("(No logs yet)");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.println(data[i]);
        }
    }
}
