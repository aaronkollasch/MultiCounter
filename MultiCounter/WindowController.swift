//
//  WindowController.swift
//  MultiCounter
//
//  Created by Aaron on 7/17/18.
//  Copyright © 2018 Aaron Kollasch. All rights reserved.
//

import Cocoa

class WindowController: NSWindowController, NSWindowDelegate {
    //Mark: Window Delegate
    // in use because AppDelegate is not quitting after the main window is closed.
    // perhaps AppDelegate thinks there are other windows open?
    func windowShouldClose(_ sender: NSWindow) -> Bool {
        NSApp.terminate(nil)
        return true
    }
}
