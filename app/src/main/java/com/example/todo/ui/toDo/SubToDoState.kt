package com.example.todo.ui.toDo


abstract class SubToDoState {

    companion object{
        class Loading() : SubToDoState() {
            companion object{
                val INSTANCE = Loading()
            }

        }
        class Loaded: SubToDoState(){
            companion object{
                val INSTANCE = Loaded()
            }
        }

        class AvailableToLoad: SubToDoState(){
            companion object{
                val INSTANCE = AvailableToLoad()
            }
        }
    }
}