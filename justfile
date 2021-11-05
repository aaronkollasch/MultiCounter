

encode-demo:
	ffmpeg -i image/Demo.mov -ss 0 -t 24 -filter:v "crop=1276:940:0:0, fps=fps=5" -vcodec libwebp -quality 75 -preset default -loop 0 -an -vsync 0 image/Demo.webp

