 #include <iostream>
using namespace std;

void goBackN(int totalSize, int window) {
    int i = 0;
    while (i < totalSize) {
        cout << "\n\nSending Window:\n";
        for (int j = 0; j < window && (i + j) < totalSize; j++) {
            cout << "Sent frame: " << (j + i + 1) << endl;
        }

        cout << "\nEnter the error on frame no (-1 if none): ";
        int error;
        cin >> error;

        if (error >= i && error < totalSize) {
            cout << "Ack received till on: " << (error - 1) << endl;
            for (int j = 0; j < error && (i + j) < totalSize; j++) {
                cout << "[" << (j + i + 1) << "]";
            }
            cout << "\nError occurred on frame no: " << error << endl;
            i = error;  
        } 
        else if (error == -1) {
            for (int j = 0; j < totalSize; j++) {
                cout << "Sent frame: " << (j + 1) << endl;
            }
            cout << "\n All frames transmitted successfully using Go-Back-N!\n";
            return;
        } 
        else {
            i += window;
        }
    }
}

void selective(int totalSize, int window, bool ack[]) {
    int i = 0;
    while (i < totalSize) {
        for (int j = 0; j < window && (i + j) < totalSize; j++) {
            int frame = (i + j + 1); //  
            cout << "Sent frame: " << frame << endl;
            cout << "Is received? (1=Yes or 0=No): ";
            int received;
            cin >> received;
            if (received == 1) {
                ack[frame - 1] = true;  
            }
        }

        for (int j = 0; j < window && (i + j) < totalSize; j++) {
            int frame = (i + j + 1);
            if (!ack[frame - 1]) {
                cout << "ReSent frame: " << frame << endl;
                cout << "Is received? (1=Yes or 0=No): ";
                int received;
                cin >> received;
                if (received == 1) {
                    ack[frame - 1] = true;
                }
            }
        }

        while (i < totalSize && ack[i]) {
            i++;
        }
    }

    cout << "\n All frames transmitted successfully using Selective Repeat!\n";
}

int main() {
    int totalSize, window;
    cout << "Enter total number of frames: ";
    cin >> totalSize;
    cout << "Enter window size: ";
    cin >> window;

    bool ack[100];   
    for (int i = 0; i < 100; i++) {
        ack[i] = false;
    }

    cout << "Operation\n1. Go-Back-N\n2. Selective\n3. Exit\nChoose protocol (1/2): ";
    int ch;
    cin >> ch;

    switch (ch) {
        case 1: goBackN(totalSize, window); break;
        case 2: selective(totalSize, window, ack); break;
        case 3: return 0;
    }

    return 0;
}
