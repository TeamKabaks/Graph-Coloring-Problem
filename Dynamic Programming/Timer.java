public class Timer {
  private static long startTime;
  private static long endTime;

  public static void start() {
      startTime = System.nanoTime();
  }

  public static void stop() {
      endTime = System.nanoTime();
  }

  public static double getTime() {
      return (endTime - startTime) / 1_000_000_000.0; // Convert nanoseconds to seconds
  }
}
