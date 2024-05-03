// можно так
task {
    name "Task_1_1_1"
    soft "2/05/2024"
    hard "5/05/2024"
}

task {
    name "Task_1_4_1"
    soft "11/06/2023"
    hard "12/01/2024"
}

task {
    name "Task_1_5_1"
    soft "11/06/2023"
    hard "12/01/2024"
}

// с студентами также
student {
    name "Kirill Romanov"
    repo "https://github.com/solloball/OOP"
    nickname "solloball"
    group "22213"
}

student {
    name "Maksim Kotenkov"
    repo "https://github.com/MurenMurenus/OOP"
    nickname "MurenMurenus"
    group "22213"
}

extra {
    studentName "Kirill Romanov"
    taskName "Task_1_1_1"
    points 1
    type "soft"
    comment "Should fix main class"
}



check {
    studentName "Kirill Romanov"
    taskName "Task_1_1_1"
}

check {
    studentName "Maksim Kotenkov"
    taskName "Task_1_1_1"
}
check {
    studentName "Maksim Kotenkov"
    taskName "Task_1_2_1"
}
check {
    studentName "Kirill Romanov"
    taskName "Task_1_5_1"
}
check {
    studentName "Maksim Kotenkov"
    taskName "Task_1_5_1"
}