import tkinter as tk
from tkinter import *
hash_table = {}

j = 1
n = 1


def add():
    global j
    global n
    n = 0
    num = text_box.get()
    i = hash(num)
    if search() == 1:
        hash_table[i] = num + str(j)
        j += 1


def search():
    global n
    if n==1:
        val = text_box1.get()
        element = hash(val)
    else:
        val = text_box.get()
        element = hash(val)

    # for key, value in hash_table.items():
    if element in hash_table.keys():
        if(n == 1):
            current = result_label.cget("text")
            result_label.config(text=current + "\n\n" + val + " is found at position " + hash_table.get(element)[
                len(hash_table.get(element)) - 1] + "☺", fg='Black')
            print(val + " is found at position " + hash_table.get(element)[:len(hash_table.get(element)) - 1])
        n = 1
        return 0

    else:
        if n == 1:
            result_label.config(text="Result: \n\n" + val + " was not found in table!!\n check your spelling\n or try with anjother word",fg='Red')
            print("value: " + str(val))
            print(val)
            print("No such element in table!!!")
        n = 1
        return 1

def view():
    print(hash_table)
    result_label.config(text="Results: \n\n")
    for key, value in hash_table.items():
        result_label.config(text= result_label.cget("text") + f"{value[len(value)-1]}: {value[:len(value)-1]}\n",fg='Black')


frame = Tk()
frame.geometry('900x600')
frame.title('Search Engine')
frame['bg'] = 'grey'
frame.resizable(height=False, width=False)

heading = Label(frame, text="  Welcome to the search engine", font=("Moshinta", 30), bg='grey', padx=30)
heading.pack()

box = Frame(frame, bg='grey')
box.pack()
text_box = Entry(frame, width=20)
text_box.place(x='560', y='200')

button1 = Button(frame, text='Add an element', bg='red', fg='White', width=20, command=add)
button1.place(x='400', y='200')
button2 = Button(frame, text='View all elements', bg='red', fg='White', width=20, command=view)
button2.place(x='400', y='300')
result = tk.Frame(frame, bg='white', height=400, width=390)
result.place(x='0',y = '100')
result_label = tk.Label(result, text="Results: \n", font=("Roboto", 15))
result_label.place(x ='0', y = '0')
result1 = Label(result, )

text_box1 = Entry(frame, width=20)
text_box1.place(x='560', y='400')

button3 = Button(frame, text='Search for an element', bg='red', fg='White', width=20, command=search)
button3.place(x='400', y='400')

menu = Menu(frame)
menu.add_cascade(label="Menu")
results = []
frame.config(menu=menu, padx=15)

frame.mainloop()
