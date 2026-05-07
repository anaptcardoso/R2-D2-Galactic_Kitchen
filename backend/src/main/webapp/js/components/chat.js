// chat.js — R2-D2 ChefBot widget

// Sets up all event listeners for the chat widget
function setupChat() {
    const fab   = document.getElementById('chat-fab');
    const win   = document.getElementById('chat-window');
    const close = document.getElementById('chat-close');
    const send  = document.getElementById('chat-send');
    const input = document.getElementById('chat-input');

    // toggle chat window visibility
    fab?.addEventListener('click',  () => win.classList.toggle('open'));

    // close chat window
    close?.addEventListener('click', () => win.classList.remove('open'));

    // send message on button click
    send?.addEventListener('click',  sendChatMessage);

    // send message on Enter key (without Shift)
    input?.addEventListener('keydown', e => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            sendChatMessage();
        }
    });
}

// Reads the input, sends the message to the API and displays the response
async function sendChatMessage() {
    const input = document.getElementById('chat-input');
    const msg = input.value.trim();
    if (!msg) return;

    addChatMsg(msg, 'user');
    input.value = '';

    try {
        const r = await ChatAPI.chat({ role: 'user', message: msg, context: null });
        addChatMsg(r.message, 'assistant');
    } catch (e) {
        addChatMsg('BEEP BOOP... Transmission failure: ' + e.message, 'assistant');
    }
}

// Appends a message bubble to the chat window
function addChatMsg(text, role) {
    const container = document.getElementById('chat-messages');
    const isAssistant = role === 'assistant';

    container.innerHTML += `
        <div class="chat-msg ${isAssistant ? 'assistant' : 'user'}">
            ${isAssistant ? '<div class="chat-msg-av">R2</div>'
                : `<div class="chat-msg-av" style="background:${getAvatarBg(App.currentUser?.firstName || 'A')};border-color:${getAvatarBorder(App.currentUser?.firstName || 'A')}">${getUserInitials()}</div>`
            }
            <div class="chat-msg-bubble">${text}</div>
            
        </div>
    `;

    // scroll to the latest message
    container.scrollTop = container.scrollHeight;
}

// Opens the chat window with a pre-filled message based on the context
function openChat(context) {
    document.getElementById('chat-window')?.classList.add('open');
    const input = document.getElementById('chat-input');
    if (input) {
        input.value = context === 'suggest'
            ? 'Suggest recipes based on the ingredients I have'
            : 'Generate a shopping list for this week';
    }
}

// Opens the chat window and displays a message from the assistant
function openChatWithMessage(msg) {
    document.getElementById('chat-window')?.classList.add('open');
    addChatMsg(msg, 'assistant');
}


