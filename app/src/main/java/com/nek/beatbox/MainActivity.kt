package com.nek.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nek.beatbox.databinding.ActivityMainBinding
import com.nek.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val constr = modelClass.getConstructor(BeatBox::class.java)
                return constr.newInstance(BeatBox(assets)) //call the constructor
            }

        }).get(MainActivityViewModel::class.java)
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //vm.beatBox = BeatBox(assets)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(vm.beatBox.sounds)
        }
        binding.seekBar.apply{
            min = 50
            max = 250
            progress = 150

            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    vm.beatBox.speed = progress.toFloat()/100.0f
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {/*empty*/}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {/*empty*/}

            })
        }
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = SoundViewModel(vm.beatBox)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings() //keep the layout in sync with the adapter (for fast updates)
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            binding.lifecycleOwner = this@MainActivity
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }
        override fun getItemCount() = sounds.size
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
