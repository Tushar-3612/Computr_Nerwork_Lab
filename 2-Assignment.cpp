

#include<iostream>
using namespace std;

int main(){
    int arr[7];
    int bit[4];

    cout << "Enter the Bits (Left to right)" << endl;
    for(int i = 0; i < 4; i++){
        cout << i+1 << " bit : ";
        cin >> bit[i];
    }

    arr[2] = bit[0];
    arr[4] = bit[1];
    arr[5] = bit[2];
    arr[6] = bit[3];

    arr[0] = 0;
    arr[1] = 0;
    arr[3] = 0;

    arr[0] = arr[2] ^ arr[4] ^ arr[6];
    arr[1] = arr[2] ^ arr[5] ^ arr[6];
    arr[3] = arr[4] ^ arr[5] ^ arr[6];

    cout << "\nSender's Hamming Code: ";
    for(int i = 0; i < 7; i++){
        cout << arr[i] << " ";
    }
    cout << endl;

    cout << "\nEnter the received 7-bit Hamming code (left to right):" << endl;
    for(int i = 0; i < 7; i++){
        cout << "Bit " << i+1 << ": ";
        cin >> arr[i];
    }

    int p1 = arr[0] ^ arr[2] ^ arr[4] ^ arr[6];
    int p2 = arr[1] ^ arr[2] ^ arr[5] ^ arr[6];
    int p4 = arr[3] ^ arr[4] ^ arr[5] ^ arr[6];

    int error_position = (p4 * 4) + (p2 * 2) + p1;

    if(error_position == 0){
        cout << "\nNo error detected in the received code." << endl;
    } else {
        cout << "\nError detected at position: " << error_position << endl;
        cout << "Fixing the bit...\n";

        arr[error_position - 1] = arr[error_position - 1] ^ 1;

        cout << "Corrected Hamming code: ";
        for(int i = 0; i < 7; i++){
            cout << arr[i] << " ";
        }
        cout << endl;
    }

    cout << "Original Data (D1 D2 D3 D4): ";
    cout << arr[2] << " " << arr[4] << " " << arr[5] << " " << arr[6] << endl;

 return 0;
}
