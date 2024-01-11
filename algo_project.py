import matplotlib.pyplot as plt
import matplotlib.animation as animation
import random
import time
import threading
import tkinter as tk


class SortingVisualizer:
    def __init__(self, root):
        self.root = root
        self.root.title("Sorting Algorithm Visualization")

        self.canvas = tk.Canvas(root, width=800, height=500)
        self.canvas.pack()

        self.frame = tk.Frame(root)
        self.frame.pack(side=tk.BOTTOM)

        self.label = tk.Label(root, text="Welcome to Group 3 Project", font=("Arial", 24))
        self.label.pack()

        self.algorithms = {
            "Bubble Sort": self.bubble_sort,
            "Selection Sort": self.selection_sort,
            "Insertion Sort": self.insertion_sort,
            "Merge Sort": self.merge_sort,
            "Quick Sort": self.quick_sort,
        }

        self.selected_algorithm = tk.StringVar()
        self.selected_algorithm.set("Bubble Sort")

        self.algorithm_menu = tk.OptionMenu(self.frame, self.selected_algorithm, *self.algorithms.keys())
        self.algorithm_menu.pack(side=tk.LEFT)

        self.start_button = tk.Button(self.frame, text="Start Sorting", command=self.start_sorting)
        self.start_button.pack(side=tk.LEFT)

    def bubble_sort(self, arr):
        start_time = time.time()
        n = len(arr)
        for i in range(n):
            for j in range(0, n - i - 1):
                if arr[j] > arr[j + 1]:
                    arr[j], arr[j + 1] = arr[j + 1], arr[j]
                    yield arr
        end_time = time.time()
        print(f"Time taken for Bubble Sort: {end_time - start_time} seconds")

    def selection_sort(self, arr):
        n = len(arr)
        for i in range(n):
            min_idx = i
            for j in range(i + 1, n):
                if arr[j] < arr[min_idx]:
                    min_idx = j
            arr[i], arr[min_idx] = arr[min_idx], arr[i]
            yield arr

    def insertion_sort(self, arr):
        for i in range(1, len(arr)):
            key = arr[i]
            j = i - 1
            while j >= 0 and key < arr[j]:
                arr[j + 1] = arr[j]
                j -= 1
            arr[j + 1] = key
            yield arr

    def merge_sort(self, arr):
        if len(arr) > 1:
            mid = len(arr) // 2
            L = arr[:mid]
            R = arr[mid:]

            yield from self.merge_sort(L)
            yield from self.merge_sort(R)

            i = j = k = 0

            while i < len(L) and j < len(R):
                if L[i] < R[j]:
                    arr[k] = L[i]
                    i += 1
                else:
                    arr[k] = R[j]
                    j += 1
                k += 1

            while i < len(L):
                arr[k] = L[i]
                i += 1
                k += 1

            while j < len(R):
                arr[k] = R[j]
                j += 1
                k += 1
            yield arr

    def quick_sort(self, arr):
        if len(arr) <= 1:
            return arr
        else:
            pivot = arr[0]
            less = [x for x in arr[1:] if x <= pivot]
            greater = [x for x in arr[1:] if x > pivot]
            return self.quick_sort(less) + [pivot] + self.quick_sort(greater)

    def visualize_sorting(self, algorithm):
        data = list(range(1, 51))  # Example data (you can change it)
        random.shuffle(data)  # Shuffle the data to demonstrate sorting

        fig, ax = plt.subplots()
        ax.set_title(f'{self.selected_algorithm.get()} Visualization')

        bar_sub = ax.bar(range(len(data)), data, align='edge')

        def update_fig(arr):
            for bar, val in zip(bar_sub, arr):
                bar.set_height(val)
                if val % 2 == 0:
                    bar.set_color('#64b5f6')  # Change color for even values
                else:
                    bar.set_color('#ff8f00')  # Change color for odd values

        ani = animation.FuncAnimation(fig, func=update_fig, frames=algorithm(data[:]), interval=50, repeat=False)
        plt.show()

    def start_sorting(self):
        selected_algo = self.selected_algorithm.get()
        algorithm_func = self.algorithms[selected_algo]
        sorting_thread = threading.Thread(target=self.visualize_sorting, args=(algorithm_func,))
        sorting_thread.start()


def main():
    root = tk.Tk()
    app = SortingVisualizer(root)
    root.mainloop()


if __name__ == "__main__":
    main()
