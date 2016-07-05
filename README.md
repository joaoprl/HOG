
HOG - Histogram of Oriented Gradients Feature Extractor


Paralelização de um extrator de features de imagens usando o método de Histograma de Gradientes Orientados.


Utilização:
Todas as imagens devem estar na pastar input, deve haver um diretório output criado.


Execute:
make - para compilar o código
make run - para rodar todas as imagens na pasta input, os dados de saída são salvos no arquivo output.dat
make clean - para limpar o código compilado e a pasta output
make magic - para rodar todas as imagens para um grupo pré-determinado de threads. A saída utilizada é a saída padrão (terminal)


Links uteis:

1. https://software.intel.com/en-us/node/529070
2. http://scikit-image.org/docs/dev/auto_examples/plot_hog.html
3. http://www.mathworks.com/help/vision/ref/extracthogfeatures.html
4. http://mccormickml.com/2013/05/09/hog-person-detector-tutorial/
5. http://mccormickml.com/2013/05/07/gradient-vectors/
6. http://lear.inrialpes.fr/people/triggs/pubs/Dalal-cvpr05.pdf
