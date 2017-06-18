# Homework 1 starter code: Simple neural network
import keras.datasets.mnist, numpy as np, os, matplotlib.pyplot as plt
os.makedirs('train', exist_ok=True)
(X, Y), (_, _) = keras.datasets.mnist.load_data()

#Y = np.random.randint(0, 9, size=Y.shape) # Uncomment this line for random labels extra credit
X = X.astype('float32').reshape((len(X), -1)).T / 255.0             # 784 x 60000
T = np.zeros((len(Y), 10), dtype='float32').T                       # 10 x 60000
for i in range(len(Y)): T[Y[i], i] = 1

#%% Setup: 784 -> 256 -> 128 -> 10

#W1 = np.random.uniform(-1/np.sqrt(784), 1/np.sqrt(784), size=(784,256)).astype('float32').T
#W2 =  np.random.uniform(-1/np.sqrt(256), 1/np.sqrt(256), size=(256,128)).astype('float32').T 
#W3 = np.random.uniform(-1/np.sqrt(128), 1/np.sqrt(128), size=(128,10)).astype('float32').T

W1 = np.random.randn(784,256).astype('float32').T/np.sqrt(784)
W2 = np.random.randn(256,128).astype('float32').T/np.sqrt(256)
W3 = np.random.randn(128,10).astype('float32').T/np.sqrt(128)

lr = 1e-5              # Learning rate, decrease if optimization isn't working
# replaced sigmoid with relu
def relu(x): return np.maximum(x,0,x)
def sigmoid(x): return 1.0/(1.0 + np.e**-x)

losses, accuracies, hw1, hw2, hw3, ma = [], [], [], [], [], []

for i in range(1000): # Do not change this, we will compare performance at 1000 epochs
    # Forward pass (use relu only in hidden layers)
    L1 = relu(W1.dot(X))
    L2 = relu(W2.dot(L1))
    L3 = sigmoid(W3.dot(L2))

    # Backward pass (only apply the relu derivative to replace the sigmoid gradient in dW2 and dW2)
    # basically an if else statement, if less than <=0 replace matrix element with 0, else just
    # retain the MSE gradient
    dW3 = (L3 - T) * L3*(1 - L3)
    dW2 = np.where(W2.dot(L1)<=0,0,W3.T.dot(dW3))
    dW1 = np.where(W1.dot(X)<=0,0,W2.T.dot(dW2))
    
    # Update
    W3 -= lr*np.dot(dW3, L2.T)
    W2 -= lr*np.dot(dW2, L1.T)
    W1 -= lr*np.dot(dW1, X.T)
    
    loss = np.sum((L3 - T)**2)/len(T.T)
    print("[%04d] MSE Loss: %0.6f" % (i, loss))
    
    """ Helpful notes:
        - Keep in mind that the visualization plots are the mean over all samples.
          Individual samples may show stronger responses.
        - Some activations and initializations may have more noticeable effects.
        - Neural networks can act like black-boxes, so don't expect too much out of the viz.
        - Neural nets can fit random labels (junk): https://arxiv.org/pdf/1611.03530.pdf 
        
        # Inspect weight 16 in layer 1
        plt.imshow(np.resize(W1[15,:], (28,28,)), cmap='gray')
    
        # Inspect weight 10 in layer 2
        plt.imshow(np.resize(W2[9,:], (16,8,)), cmap='gray')
    """
    #%% Additional monitoring code - DO NOT EDIT THIS PART
    losses.append(loss)        
    if i % 10 == 0:
        predictions = np.zeros(L3.shape, dtype='float32')
        for j, m in enumerate(np.argmax(L3.T, axis=1)): predictions[m,j] = 1
        acc = np.sum(predictions*T)
        accpct = 100*acc/X.shape[1]
        accuracies.append(accpct)   
        
        fig, (ax1, ax2, ax3) = plt.subplots(1, 3, figsize=(12,3))
        testi = np.random.choice(range(60000))
        ax1.imshow(X.T[testi].reshape(28,28), cmap='gray')
        ax1.set_xticks([]), ax1.set_yticks([])
        cls = np.argmax(L3.T[testi])
        ax1.set_title("Prediction: %d confidence=%0.2f" % (cls, L3.T[testi][cls]/np.sum(L3.T[testi])))
        
        ax2.plot(losses, color='blue')
        ax2.set_title("Loss"), ax2.set_yscale('log')
        ax3.plot(accuracies, color='blue')
        ax3.set_ylim([0, 100])
        ax3.axhline(90, color='red', linestyle=':')     # Aim for 90% accuracy in 200 epochs        
        ax3.set_title("Accuracy: %0.2f%%" % accpct)
        plt.show(), plt.close()

        fig, ((ax1, ax2, ax3, ax4), (ax5, ax6, ax7, ax8), (ax9, ax10, ax11, ax12)) = plt.subplots(3, 4, figsize=(10,10))
        ax1.imshow(np.reshape(L1.mean(axis=1), (16, 16,)), cmap='gray', interpolation='none'), ax1.set_title('L1 $\mu$=%0.2f $\sigma$=%0.2f' % (L1.mean(), L1.std()))
        ax2.imshow(np.reshape(L2.mean(axis=1), (16, 8,)),  cmap='gray', interpolation='none'), ax2.set_title('L2 $\mu$=%0.2f $\sigma$=%0.2f' % (L2.mean(), L2.std()))
        ax3.imshow(np.reshape(L3.mean(axis=1), (10, 1,)),  cmap='gray', interpolation='none'), ax3.set_title('L3 $\mu$=%0.2f $\sigma$=%0.2f' % (L3.mean(), L3.std())), ax3.set_xticks([])
        activations = np.concatenate((L1.flatten(), L2.flatten(), L3.flatten()))
        ma.append(activations.mean())
        ax4.plot(ma, color='blue'), ax4.set_title("Activations $\mu$: %0.2f $\sigma$=%0.2f" % (ma[-1], activations.std()))
        ax4.set_ylim(0, 1)

        ax5.imshow(np.reshape(W1.mean(axis=0), (28, 28,)), cmap='gray', interpolation='none'), ax5.set_title('W1 $\mu$=%0.2f $\sigma$=%0.2f' % (W1.mean(), W1.std()))
        ax6.imshow(np.reshape(W2.mean(axis=0), (16, 16,)), cmap='gray', interpolation='none'), ax6.set_title('W2 $\mu$=%0.2f $\sigma$=%0.2f' % (W2.mean(), W2.std()))
        ax7.imshow(np.reshape(W3.mean(axis=0), (16, 8, )), cmap='gray', interpolation='none'), ax7.set_title('W3 $\mu$=%0.2f $\sigma$=%0.2f' % (W3.mean(), W3.std())), ax7.set_xticks([])
        ax8.plot(accuracies, color='blue'), ax8.set_title("Accuracy: %0.2f%%" % accpct), ax8.set_ylim(0, 100)
        
        uw1, uw2, uw3 = np.dot(dW1, X.T), np.dot(dW2, L1.T), np.dot(dW3, L2.T)
        hw1.append(lr*np.abs(uw1).mean()), hw2.append(lr*np.abs(uw2).mean()), hw3.append(lr*np.abs(uw3).mean())
        ax9.imshow(np.reshape(uw1.sum(axis=0),  (28, 28,)), cmap='gray', interpolation='none'), ax9.set_title ('$\Delta$W1: %0.2f E-5' % (1e5 * lr * np.abs(uw1).mean()), color='r')
        ax10.imshow(np.reshape(uw2.sum(axis=0), (16, 16,)), cmap='gray', interpolation='none'), ax10.set_title('$\Delta$W2: %0.2f E-5' % (1e5 * lr * np.abs(uw2).mean()), color='g')
        ax11.imshow(np.reshape(uw3.sum(axis=0), (16, 8, )), cmap='gray', interpolation='none'), ax11.set_title('$\Delta$W3: %0.2f E-5' % (1e5 * lr * np.abs(uw3).mean()), color='b'), ax11.set_xticks([])    
        ax12.plot(hw1, color='r'), ax12.plot(hw2, color='g'), ax12.plot(hw3, color='b'), ax12.set_title('Weight update magnitude')
        ax12.legend(loc='upper right'), ax12.set_yscale('log')
        
        plt.suptitle("Weight and update visualization ACC: %0.2f%% LR=%0.8f" % (accpct, lr))
        plt.savefig(os.path.join('relu/train', 'train-%05d.png' % i))
        plt.show(), plt.close()
        
#%% Final Plot for tanh loss and accuracy        
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12,3))
# loss plot on a log scale
ax1.plot(losses, color='blue')
ax1.set_title("Loss"), ax2.set_yscale('log')
# accuracies plot that shows 90% a desired goal
ax2.plot(accuracies, color='blue')
ax2.set_ylim([0, 100])
ax2.axhline(90, color='red', linestyle=':')     # Aim for 90% accuracy in 200 epochs   
# display the accuracy for the current epoch
ax2.set_title("Accuracy: %0.2f%%" % accpct)
plt.suptitle("Relu")
plt.savefig(os.path.join('relu', 'relu.png'))