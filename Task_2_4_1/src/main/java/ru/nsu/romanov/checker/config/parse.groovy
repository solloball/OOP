package ru.nsu.romanov.checker.config

def task(Closure cl) {
    Task t = new Task()
    cl.delegate = t
    cl.call()
    config.tasks.add(t)
}

def student(Closure cl) {
    Student s = new Student();
    cl.delegate = s
    cl.call()
    config.students.add(s)
}

def extra(Closure cl) {
    ExtraPoints extra = new ExtraPoints();
    cl.delegate = extra
    cl.call()
    config.extraPoints.add(extra)
}

def check(Closure cl) {
    Check check = new Check();
    cl.delegate = check
    cl.call()
    config.checks.add(check)
}
