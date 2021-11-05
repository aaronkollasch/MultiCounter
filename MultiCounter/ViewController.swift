//
//  ViewController.swift
//  MultiCounter
//
//  Created by Aaron on 7/16/18.
//  Copyright © 2018 Aaron Kollasch. All rights reserved.
//

import Cocoa

class PlaceholderTextView: NSTextView {
    @objc var placeholderAttributedString: NSAttributedString? = NSAttributedString(
        string: "Enter commands here…",
        attributes: [NSAttributedString.Key.foregroundColor: NSColor.lightGray]
    )
}

class ViewController: NSViewController {
    var entryText: String = ""
    var addList = [Int]()
    var countList: [[Int]] = [[Int]]()
    var myTextView: NSTextView!
    var myEntryTable: NSTableView!
    var myOutputTable: NSTableView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.setUpSampleData()
        
        for v: NSView in getRecursiveSubviews(view: self.view) {
            switch v.identifier?.rawValue {
            case "InputBox":
                myTextView = v as? NSTextView
            case "EntryTable":
                myEntryTable = v as? NSTableView
            case "OutputTable":
                myOutputTable = v as? NSTableView
            default:
                break
            }
        }
        if myTextView != nil {
            myTextView.textStorage?.setAttributedString(NSAttributedString(string: entryText))
            myTextView.delegate = self
            myTextView.updateLayer()
        }
        if myOutputTable != nil {
            //myOutputTable.userActivity = nil
        }
        myEntryTable.tableColumn(withIdentifier: NSUserInterfaceItemIdentifier(rawValue: "AddColumn"))?.tableView?.sizeToFit()
    }

    override var representedObject: Any? {
        didSet {
            // Update the view, if already loaded.
        }
    }
    
    func getRecursiveSubviews(view: NSView) -> [NSView] {
        var views: [NSView] = [view]
        for view: NSView in view.subviews {
            views += getRecursiveSubviews(view: view)
        }
        return views
    }

    func setUpSampleData() {
//        entryText = "jklkj jkjjkjs sdf"
        addList = getAddListFromString(countString: entryText)
        countList = getCountListFromString(countString: entryText)
    }
    
    /**
     Gets the most recent column of additions from a string of commands
     
     - Parameter countString: the string of commands
     
     - Returns: An Int array containing the column of additions (recent first)
     */
    func getAddListFromString(countString: String) -> [Int] {
        var newList: [Int] = []
        loopOverString: for c in countString.lowercased().reversed() {
            switch c {
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                    newList.append(Int(String(c))!)
                case "f", "j":
                    newList.append(1)
                case "d", "k":
                    newList.append(2)
                case "s", "l":
                    newList.append(3)
                case "a", ";":
                    newList.append(4)
                case " ", "\t", "\n":
                    break loopOverString
                case "x":
                    break loopOverString
                case "c":
                    break loopOverString
                default:
                    break
            }
        }
        return newList
    }
    
    /**
     Parses a string of commands to obtain all columns of additions
     
     If 'c' is present at the end of countString, copies the count list generated up to that point.
     
     - Parameter countString: the string of commands
     
     - Returns: [[Int]] containing the columns of additions (oldest first)
     */
    func getCountListFromString(countString: String) -> [[Int]] {
        var newLists: [[Int]] = []
        var newList: [Int] = []
        var addInt = 0
        var addLast = false
        var addLastColumn = true
        for c in countString.lowercased() {
            addLastColumn = true
            switch c {
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                    addInt += Int(String(c))!
                    addLast = true
                case "f", "j":
                    addInt += 1
                    addLast = true
                case "d", "k":
                    addInt += 2
                    addLast = true
                case "s", "l":
                    addInt += 3
                    addLast = true
                case "a", ";":
                    addInt += 4
                    addLast = true
                case " ", "\t":
                    newList.append(addInt)
                    addInt = 0
                    addLast = false
                case "\n":
                    if (addLast) { newList.append(addInt) }
                    addInt = 0
                    newLists.append(newList)
                    newList = []
                    addLast = false
                    addLastColumn = true
                case "x":
                    addInt = 0
                    addLast = false
                case "c":
                    if (addLast) { newList.append(addInt) }
                    addInt = 0
                    newLists.append(newList)
                    newList = []
                    addLast = false
                    addLastColumn = false
                default:
                    break
            }
        }
        if addLast {
            newList.append(addInt)
        }
        if addLastColumn {
            newLists.append(newList)
        }
        if (countString.last == "c") {
            copyTableToClipboard(copyTable: newLists)
        }
        return newLists
    }

    func copyTableToClipboard(copyTable: [[Int]]) {
        let pasteBoard = NSPasteboard.general
        pasteBoard.clearContents()
        var pasteString: String = ""
        
        let transpTable = transposeRowsAndColumns(arr: copyTable)
        for column: [String] in transpTable {
            for element: String in column {
                pasteString += element
                pasteString.append("\t" as Character)
            }
            pasteString.remove(at: pasteString.index(before: pasteString.endIndex))
            pasteString.append("\n" as Character)
        }
        pasteBoard.writeObjects([NSString(string: pasteString)])
    }
    
    func transposeRowsAndColumns<T>(arr: [[T]]) -> [[String]]
    {
        let rowCount = arr.count;
        let columnCount = longestListLength(array2D: arr);
        var transposed: [[String]] = [[String]](repeating: [String](repeating: "", count: rowCount), count: columnCount)

        for (index, _) in transposed.enumerated() {
            transposed[index] = [String](repeating: "", count:rowCount);
            for (jndex, _) in transposed[index].enumerated()
            {
                if jndex < arr.count && index < arr[jndex].count {
                    transposed[index][jndex] = "\(arr[jndex][index])";
                }
            }
        }
        return transposed;
    }
    
    func refreshTables() {
        myEntryTable.reloadData()
        var addSum = 0
        for addition: Int in addList {
            addSum += addition
        }
        myEntryTable.tableColumn(withIdentifier: NSUserInterfaceItemIdentifier(rawValue: "AddColumn"))!.headerCell.stringValue = "Total: \(addSum)"
        
        let intendedColumnCount = countList.count
        if myOutputTable.tableColumns.count > intendedColumnCount {
            while myOutputTable.tableColumns.count > intendedColumnCount {
                myOutputTable.removeTableColumn(myOutputTable.tableColumns.last!)
                updateOutputTableColumns()
            }
        }
        else if myOutputTable.tableColumns.count < intendedColumnCount {
            while myOutputTable.tableColumns.count < intendedColumnCount {
                let columnNumber = myOutputTable.tableColumns.count + 1
                let ident = "Column_\(columnNumber)"
                let newColumn: NSTableColumn = NSTableColumn.init(identifier: NSUserInterfaceItemIdentifier(rawValue: ident))
                newColumn.width = 70
                myOutputTable.addTableColumn(newColumn)
                updateOutputTableColumns()
            }
        }
        myOutputTable.reloadData()
        myOutputTable.scrollRowToVisible(countList[0].count-1)
    }
    
    func longestListLength<T>(array2D: [[T]]) -> Int {
        var max = 0
        for list in array2D {
            if (list.count > max) {
                max = list.count
            }
        }
        return max
    }
    
    func updateOutputTableColumns() {
        for (index, column) in myOutputTable.tableColumns.enumerated() {
            column.identifier = NSUserInterfaceItemIdentifier(rawValue: "Column_\(index + 1)")
            column.headerCell.stringValue = "Counts \(myOutputTable.numberOfColumns - index)"
        }
    }
}

// MARK: - NSTextViewDelegate
extension ViewController: NSTextViewDelegate {
    func textViewDidChangeSelection(_ notification: Notification) {
        entryText = (myTextView.textStorage?.string)!
        addList = getAddListFromString(countString: entryText)
        countList = getCountListFromString(countString: entryText)
        refreshTables()
    }
}

// MARK: - NSTableViewDataSource
extension ViewController: NSTableViewDataSource {
    func numberOfRows(in tableView: NSTableView) -> Int {
        if tableView.identifier?.rawValue == "OutputTable" {
            return longestListLength(array2D: countList)
        }
        else {
            return self.addList.count
        }
    }
    
    func tableView(_ tableView: NSTableView, objectValueFor tableColumn: NSTableColumn?, row: Int) -> Any? {
        let tcIdentifier: String = tableColumn!.identifier.rawValue
        if tcIdentifier == "AddColumn" {
            if row < self.addList.count {
                let addDoc = self.addList[row]
                return NSString(string: String(addDoc))
            }
        }
        else if tcIdentifier.contains("Column_") {
            let columnNumberString: String = String(tcIdentifier[tcIdentifier.index(tcIdentifier.firstIndex(of: "_")!, offsetBy: 1)...])
            let columnNumber = Int(columnNumberString)!
            let columnIndex = tableView.numberOfColumns - columnNumber
            if columnIndex < self.countList.count && row < self.countList[columnIndex].count {
                let countDoc = self.countList[columnIndex][row]
                return NSString(string: String(countDoc))
            }
        }
        return ""
    }
}

// MARK: - NSTableViewDelegate
extension ViewController: NSTableViewDelegate {
    
}

