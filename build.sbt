lazy val root = (project in file(".")).
  settings(
    name := "mandelbrot",
    version := "1.0",
    fork in run := true
  )
