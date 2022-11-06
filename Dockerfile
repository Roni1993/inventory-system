FROM ubuntu:22.04

RUN cd
RUN git clone https://github.com/Roni1993/.dotfiles
RUN cd .dotfiles
RUN ./install

RUN chsh -s /usr/bin/zsh
RUN zsh
RUN zi update
