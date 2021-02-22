//
//  ViewController.swift
//  labs
//
//  Created by Oleg on 22.02.2021.
//

import UIKit
import Foundation

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Частина 1

        // Дано рядок у форматі "Student1 - Group1; Student2 - Group2; ..."

        let studentsStr = "Дмитренко Олександр - ІП-84; Матвійчук Андрій - ІВ-83; Лесик Сергій - ІО-82; Ткаченко Ярослав - ІВ-83; Аверкова Анастасія - ІО-83; Соловйов Даніїл - ІО-83; Рахуба Вероніка - ІО-81; Кочерук Давид - ІВ-83; Лихацька Юлія - ІВ-82; Головенець Руслан - ІВ-83; Ющенко Андрій - ІО-82; Мінченко Володимир - ІП-83; Мартинюк Назар - ІО-82; Базова Лідія - ІВ-81; Снігурець Олег - ІВ-81; Роман Олександр - ІО-82; Дудка Максим - ІО-81; Кулініч Віталій - ІВ-81; Жуков Михайло - ІП-83; Грабко Михайло - ІВ-81; Іванов Володимир - ІО-81; Востриков Нікіта - ІО-82; Бондаренко Максим - ІВ-83; Скрипченко Володимир - ІВ-82; Кобук Назар - ІО-81; Дровнін Павло - ІВ-83; Тарасенко Юлія - ІО-82; Дрозд Світлана - ІВ-81; Фещенко Кирил - ІО-82; Крамар Віктор - ІО-83; Іванов Дмитро - ІВ-82"

        // Завдання 1
        // Заповніть словник, де:
        // - ключ – назва групи
        // - значення – відсортований масив студентів, які відносяться до відповідної групи

        var studentsGroups: [String: [String]] = [:]
        
        // Ваш код починається тут
        
        let splittedStudents = studentsStr.components(separatedBy: "; ")
        for i in 0...splittedStudents.count-1 {
            let splittedGroups = splittedStudents[i].components(separatedBy: " - ")
            
            var students: [String] = studentsGroups[splittedGroups[1]] ?? [String]()
            students.append(splittedGroups[0])
            studentsGroups[splittedGroups[1]] = students
            studentsGroups[splittedGroups[1]]?.sort()
        }
        
        // Ваш код закінчується тут

        print("Завдання 1")
        print(studentsGroups)
        print()

        // Дано масив з максимально можливими оцінками

        let points: [Int] = [12, 12, 12, 12, 12, 12, 12, 16]

        // Завдання 2
        // Заповніть словник, де:
        // - ключ – назва групи
        // - значення – словник, де:
        //   - ключ – студент, який відносяться до відповідної групи
        //   - значення – масив з оцінками студента (заповніть масив випадковими значеннями, використовуючи функцію `randomValue(maxValue: Int) -> Int`)

        func randomValue(maxValue: Int) -> Int {
            switch(arc4random_uniform(6)) {
            case 1:
                return Int(ceil(Float(maxValue) * 0.7))
            case 2:
                return Int(ceil(Float(maxValue) * 0.9))
            case 3, 4, 5:
                return maxValue
            default:
                return 0
            }
        }

        var studentPoints: [String: [String: [Int]]] = [:]

        // Ваш код починається тут

        for (group, students) in studentsGroups {
            studentPoints[group] = [:]
            for i in 0...students.count - 1 {
                if studentPoints[group] == nil {
                    studentPoints[group] = [String:[Int]]()
                }
                var pointsArray: [Int] = []
                for j in 0...points.count-1 {
                    pointsArray.append(randomValue(maxValue:(points[j])))
                }
                studentPoints[group]?[students[i]] = pointsArray
            }
        }

        // Ваш код закінчується тут

        print("Завдання 2")
        print(studentPoints)
        print()

        // Завдання 3
        // Заповніть словник, де:
        // - ключ – назва групи
        // - значення – словник, де:
        //   - ключ – студент, який відносяться до відповідної групи
        //   - значення – сума оцінок студента

        var sumPoints: [String: [String: Int]] = [:]

        // Ваш код починається тут
        for (group, studentAndPoints) in studentPoints {
            sumPoints[group] = [String:Int]()
            for (student, marks) in studentAndPoints {
                sumPoints[group]?[student] = marks.reduce(0, {x,y in x+y})
            }
        }


        // Ваш код закінчується тут

        print("Завдання 3")
        print(sumPoints)
        print()

        // Завдання 4
        // Заповніть словник, де:
        // - ключ – назва групи
        // - значення – середня оцінка всіх студентів групи

        var groupAvg: [String: Float] = [:]

        // Ваш код починається тут
        for (group, studentToMarks) in sumPoints {
            groupAvg[group] = Float(studentToMarks.values.reduce(0, {x, y in x+y})) / Float(studentToMarks.values.count)
        }


        // Ваш код закінчується тут

        print("Завдання 4")
        print(groupAvg)
        print()

        // Завдання 5
        // Заповніть словник, де:
        // - ключ – назва групи
        // - значення – масив студентів, які мають >= 60 балів

        var passedPerGroup: [String: [String]] = [:]

        // Ваш код починається тут
        for (group, studentToMarks) in sumPoints {
            if passedPerGroup[group] == nil {
                passedPerGroup[group] = [String]()
            }
            studentToMarks.forEach { arg0 in
                let (student, mark) = arg0
                if mark >= 60 {
                    var array = passedPerGroup[group]
                    array?.append(student)
                    passedPerGroup[group] = array
                }
            }
        }


        // Ваш код закінчується тут

        print("Завдання 5")
        print(passedPerGroup)

        let time1: TimeOS = TimeOS()
        let time2: TimeOS = TimeOS(seconds: 10, minutes: 10, hours: 10)
        let time3: TimeOS = TimeOS(date: Date())
        print(time1.toString())
        print(time2.toString())
        print(time3.toString())
        
        print(time1.Add(timeOS: time2).toString())
        print(time1.Sub(timeOS: time2).toString())
        
        print(TimeOS.Add(time1: time2, time2: time3).toString())
        print(TimeOS.Sub(time1: time2, time2: time3).toString())
    }

}

class TimeOS {
    var seconds: UInt
    var minutes: UInt
    var hours: UInt
    init () {
        self.seconds = 0
        self.minutes = 0
        self.hours = 0
    }
    
    convenience init(date: Date) {
        let calendar = Calendar.current
        self.init(seconds: UInt(calendar.component(.second, from: date)), minutes: UInt(calendar.component(.minute, from: date)), hours: UInt(calendar.component(.hour, from: date)))
    }
    
    init(seconds: UInt, minutes: UInt, hours: UInt) {
        if seconds > 59 {
            self.seconds = 59
        } else {
            self.seconds = seconds
        }
        if minutes > 59 {
            self.minutes = 59
        } else {
            self.minutes = minutes
        }
        if hours > 23 {
            self.hours = 23
        } else {
            self.hours = hours
        }
    }
    func toString() -> String {
        var localHours = hours
        var AM: String = "AM"
        if localHours > 12 {
            localHours-=12
            AM = "PM"
        }
        return "\(localHours):\(minutes):\(seconds) \(AM)"
    }
    func Add(timeOS: TimeOS) -> TimeOS {
        var NewHours = self.hours + timeOS.hours
        var NewMinutes = self.minutes + timeOS.minutes
        var NewSeconds = self.seconds + timeOS.seconds
        
        if NewSeconds > 59 {
            NewSeconds -= 60
            NewMinutes += 1
        }
        if NewMinutes > 59 {
            NewMinutes -= 60
            NewHours += 1
        }
        if NewHours > 23 {
            NewHours -= 24
        }
        
        return TimeOS(seconds: NewSeconds, minutes: NewMinutes, hours: NewHours)
    }
    func Sub(timeOS: TimeOS) -> TimeOS {
        let NewHours:UInt
        let NewMinutes:UInt
        let NewSeconds:UInt
        if self.hours >= timeOS.hours {
            NewHours = self.hours - timeOS.hours
        } else {
            NewHours = 23+self.hours - timeOS.hours
        }
        if self.minutes >= timeOS.minutes {
            NewMinutes = self.minutes - timeOS.minutes
        } else {
            NewMinutes = 60+self.minutes - timeOS.minutes
        }
        if self.seconds >= timeOS.seconds {
            NewSeconds = self.seconds - timeOS.seconds
        } else {
            NewSeconds = 60 + self.seconds - timeOS.seconds
        }
        return TimeOS(seconds: NewSeconds, minutes: NewMinutes, hours: NewHours)
    }
    
    static func	Add(time1: TimeOS, time2: TimeOS) -> TimeOS {
        return time1.Add(timeOS: time2)
    }
    static func Sub(time1: TimeOS, time2: TimeOS) -> TimeOS {
        return time1.Sub(timeOS: time2)
    }
}



